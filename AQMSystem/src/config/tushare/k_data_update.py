from sqlalchemy import create_engine
import pymysql
import tushare as ts
import time

database = 'AQM'

date = time.strftime('%Y-%m-%d',time.localtime(time.time()))
start_date = date
end_date = date

# 创建数据库连接
engine = create_engine('mysql+pymysql://root:123456@localhost:3306/%s?charset=utf8' % database)

# 连接数据库
db = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='123456', db=database, charset='utf8')

# 获取所有股票信息
df = ts.get_stock_basics()[['name']].sort_index()
for code in df.index:
    sql = 'select * from stock where code = %s;'
    cursor = db.cursor()
    cursor.execute(sql, code)
    # 新股
    if cursor.fetchone() is None:
        print('New stock code: ', code)
        insert_sql = 'insert into stock (code, name) values (%s, %s);'
        cursor.execute(insert_sql, (code, df.loc[code, 'name']))
        insert_sql = 'insert into block_stock (block_name, block_type, code) values (%s, %s, %s);'
        # 主板类型判断
        if code[:3] == '600' or code[:3] == '601' or code[:3] == '000' or code[:3] == '001':
            cursor.execute(insert_sql, ('主板', 'main', code))
        # 中小板类型判断
        elif code[:3] == '002':
            cursor.execute(insert_sql, ('中小板', 'small', code))
        # 创业板类型判断
        elif code[:3] == '300':
            cursor.execute(insert_sql, ('创业板', 'gem', code))

        # 地域板块信息
        df_temp = ts.get_area_classified()
        if not df_temp[df_temp.code == code].empty:
            df_temp = df_temp[['code', 'area']][df_temp.code == code]
            df_temp['block_type'] = 'area'
            df_temp.rename(columns={'area': 'block_name'}, inplace=True)
            df_temp.to_sql('block_stock', engine, index=False, if_exists='append')

        # 行业板块信息
        df_temp = ts.get_industry_classified()
        if not df_temp[df_temp.code == code].empty:
            df_temp = df_temp[['code', 'c_name']][df_temp.code == code]
            df_temp['block_type'] = 'industry'
            df_temp.rename(columns={'c_name': 'block_name'}, inplace=True)
            df_temp.to_sql('block_stock', engine, index=False, if_exists='append')

        # 概念板块信息
        df_temp = ts.get_concept_classified()
        if not df_temp[df_temp.code == code].empty:
            df_temp = df_temp[['code', 'c_name']][df_temp.code == code]
            df_temp['block_type'] = 'concept'
            df_temp.rename(columns={'c_name': 'block_name'}, inplace=True)
            df_temp.to_sql('block_stock', engine, index=False, if_exists='append')

        create_sql = """create table if not exists k_data_%s (
                        code varchar(6) not null,
                        date date not null,
                        open double precision not null,
                        high double precision not null,
                        low double precision not null,
                        close double precision not null,
                        volume integer not null,
                        adj_close double precision not null,
                        primary key (code, date)
                        ) engine=MyISAM DEFAULT CHARSET=utf8""" % code
        cursor.execute(create_sql)

    db.commit()

db.close()

# 板块指数历史数据
plate_index = ['hs300', 'sz', 'sh', 'cyb', 'zxb']
for code in plate_index:
    # 不复权数据
    df = ts.get_k_data(code, autype='None', start=start_date, end=end_date)
    if df.empty:
        continue
    # 复权数据
    df['adj_close'] = ts.get_k_data(code, autype='qfq', start=start_date, end=end_date)[['close']]

    df.to_sql('k_data_%s' % code, engine, index=False, if_exists='append')

print("Finish read plate index")

# 获取股票历史数据
count = 0
stocks = ts.get_stock_basics()
for code in stocks.index:
    count += 1
    print('%4d\t%s' % (count, code))

    # 不复权数据
    df = ts.get_k_data(code, autype='None', start=start_date, end=end_date)
    # 所选日期内无数据
    if df.empty:
        continue
    # 获取复权收盘价
    df['adj_close'] = ts.get_k_data(code, autype='qfq', start=start_date, end=end_date)[['close']]

    df.to_sql('k_data_%s' % code, engine, index=False, if_exists='append')

print("Finish read stock data")
