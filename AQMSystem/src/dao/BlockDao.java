package dao;


import config.BlockType;
import config.IndexType;
import config.TableInterceptor;
import dataservice.BlockDataService;
import model.Block;
import model.KData;
import model.Stock;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.*;


@Repository
@SuppressWarnings("unchecked")
public class BlockDao implements BlockDataService {

    private final SessionFactory sessionFactory;

    private static final String PLATE_INDEX_TABLE_NAME = "k_data";

    @Autowired
    public BlockDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 根据板块类型获取拦截器
     *
     * @param block
     * @return Interceptor
     */
    private Interceptor PlateIndexInterceptor(String block) {
        String tempTableName = PLATE_INDEX_TABLE_NAME + "_" + block;
        return new TableInterceptor(PLATE_INDEX_TABLE_NAME, tempTableName);
    }

    @Override
    public List<Stock> findBlockStocks(BlockType blockType, String... blocks) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            List<String> blockList = new ArrayList<>();

            if (blocks.length == 0) {
                List<Block> entities = findBlocksByType(blockType);
                for (Block entity : entities) {
                    blockList.add(entity.getBlockName());
                }
            } else {
                blockList.addAll(Arrays.asList(blocks));
            }

            List<String> codes = new ArrayList<>();
            for (String block : blockList) {
                String sql = "select distinct code from block_stock where block_type = \'"
                        + blockType.getBlockForShort() + "\' and block_name = \'" + block + "\'";
                codes.addAll(session.createNativeQuery(sql).list());
            }
            Collections.sort(codes);

            List<Stock> stocks = new ArrayList<>();
            for (String code : codes) {
                stocks.add(session.get(Stock.class, code));
            }

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
    public List<Block> findBlocksByType(BlockType blockType) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            List<Block> blocks = session
                    .createQuery("from Block b where b.blockType = :block_type")
                    .setParameter("block_type", blockType.getBlockForShort())
                    .list();

            tx.commit();
            return blocks;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Block> findBlocksByStock(String code) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            String sql = "select block_name, block_type from block_stock where code = \'" + code +"\';";

            List<Block> result = session
                    .createNativeQuery(sql, Block.class)
                    .list();

            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public int countBlockStocks(BlockType blockType, String blockName) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            String sql = "select count(*) from block_stock where block_type = " +
                    "\'" + blockType.getBlockForShort() +  "\'" + " and block_name = \'" + blockName + "\';";

            int result = ((BigInteger) session.createNativeQuery(sql).uniqueResult()).intValue();

            tx.commit();
            return result;
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<KData> getPlateIndex(IndexType indexType, Date begin, Date end) {
        Transaction tx = null;
        try (Session session = sessionFactory
                .withOptions()
                .interceptor(PlateIndexInterceptor(indexType.getPlateIndexForShort()))
                .openSession()) {

            tx = session.beginTransaction();

            List<KData> result = session
                    .createQuery("FROM KData k WHERE k.date >= :begin AND k.date <= :end order by k.date")
                    .setParameter("begin", begin)
                    .setParameter("end", end)
                    .list();

            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

}
