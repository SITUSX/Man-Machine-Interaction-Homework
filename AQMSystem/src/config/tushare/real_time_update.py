import tushare as ts
import pymysql

database = "AQM"

db = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='123456', db=database, charset='utf8')

df = ts.get_today_all()[['code', 'name', 'changepercent', 'trade', 'open', 'high', 'low', 'volume']]

sql = 'select * from newest where code = %s;'
for index in df.index:
    stock = df.iloc[index]
    cursor = db.cursor()
    cursor.execute(sql, stock['code'])
    if cursor.fetchone() is None:
        insert_sql = 'insert into newest (code, name, change_percent, trade, open, high, low, volume) values (%s, %s, %s, %s, %s, %s, %s, %s);'
        cursor.execute(insert_sql, (stock['code'],
                                    stock['name'],
                                    float(stock['changepercent']),
                                    float(stock['trade']),
                                    float(stock['open']),
                                    float(stock['high']),
                                    float(stock['low']),
                                    int(stock['volume'])))
    else:
        update_sql = 'update newest set change_percent=%s, trade=%s, open=%s, high=%s, low=%s, volume=%s where code=%s;'
        cursor.execute(update_sql, (float(stock['changepercent']),
                                    float(stock['trade']),
                                    float(stock['open']),
                                    float(stock['high']),
                                    float(stock['low']),
                                    int(stock['volume']),
                                    stock['code']))
    db.commit()

print('\nFinish updating real time data.')
db.close()
