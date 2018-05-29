package dao;

import config.TableInterceptor;
import dataservice.StockDataService;
import model.KData;
import model.Newest;
import model.Stock;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("unchecked")
public class StockDao implements StockDataService {

    private final SessionFactory sessionFactory;

    private static final String K_DATA_TABLE_NAME = "k_data";

    @Autowired
    public StockDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 根据股票代码获取拦截器
     *
     * @param code
     * @return Interceptor
     */
    private Interceptor KDataInterceptor(String code) {
        String tempTableName = K_DATA_TABLE_NAME + "_" + code;
        return new TableInterceptor(K_DATA_TABLE_NAME, tempTableName);
    }

    @Override
    public Stock findStock(String code) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Stock stock = session.get(Stock.class, code);
            tx.commit();
            return stock;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Stock> searchStock(String filter) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            List<Stock> stocks = session
                    .createQuery("FROM Stock s WHERE s.code like :filter or s.name like :filter order by s.code")
                    .setParameter("filter", "%" + filter + "%")
                    .list();

            tx.commit();

            return stocks;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<KData> getStockRecords(String code) {
        Transaction tx = null;
        try (Session session = sessionFactory
                .withOptions()
                .interceptor(KDataInterceptor(code))
                .openSession()) {

            tx = session.beginTransaction();
            List<KData> kDataList = session
                    .createQuery("from KData k where k.code=:code order by k.date")
                    .setParameter("code", code)
                    .list();
            tx.commit();
            return kDataList;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<KData> getStockRecords(String code, Date begin, Date end) {
        Transaction tx = null;
        try (Session session = sessionFactory
                .withOptions()
                .interceptor(KDataInterceptor(code))
                .openSession()) {

            tx = session.beginTransaction();
            List<KData> kDataList = session
                    .createQuery("from KData k where k.date >= :begin and k.date <= :end order by k.date")
                    .setParameter("begin", begin)
                    .setParameter("end", end)
                    .list();
            tx.commit();
            return kDataList;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    /**
     * 获取所有A股信息
     */
    private List<Stock> getAllStocks() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<Stock> stocks = session.createQuery("from Stock").list();
            tx.commit();
            return stocks;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    /**
     * 获取某天和其前一天的k线数据
     * @param code 股票代码
     * @param date 日期
     * @return KData[]
     */
    private KData[] getInfoByDate(String code, Date date) {
        Transaction tx = null;
        try (Session session = sessionFactory
                .withOptions()
                .interceptor(KDataInterceptor(code))
                .openSession()) {

            tx = session.beginTransaction();
            KData[] kDataInfo = new KData[2];

            kDataInfo[0] = (KData) session
                    .createQuery("from KData k1 " +
                            "where k1.date = (select max(k2.date) from KData k2 where k2.date <= :date)")
                    .setParameter("date", date)
                    .uniqueResult();

            kDataInfo[1] = (KData) session
                    .createQuery("from KData k1 " +
                            "where k1.date = (select max(k2.date) from KData k2 where k2.date < :date)")
                    .setParameter("date", kDataInfo[0].getDate())
                    .uniqueResult();

            tx.commit();
            return kDataInfo;
        } catch (RuntimeException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            throw e;
            return null;
        }
    }

    @Override
    public List<KData[]> getMarketInfoByDate(Date date) {
        List<Stock> stocks = getAllStocks();
        return stocks.stream()
                .map(s -> getInfoByDate(s.getCode(), date))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public KData[] getNewestInfo(String code) {
        return getInfoByDate(code, new Date());
    }

    @Override
    public Newest findNewest(String code) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Newest newest = session.get(Newest.class, code);
            tx.commit();
            return newest;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

}
