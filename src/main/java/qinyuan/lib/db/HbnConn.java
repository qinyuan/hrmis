package qinyuan.lib.db;

import java.io.Serializable;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.type.StandardBasicTypes;

public class HbnConn implements AutoCloseable {

	private static SessionFactory sf;
	static {
		Configuration conf = new Configuration().configure();
		sf = conf.buildSessionFactory();
	}

	private Session sess;
	private Transaction tx;
	private SQLQuery sqlQuery;

	public HbnConn() {
		sess = sf.openSession();
		tx = sess.beginTransaction();
	}

	@Override
	public void close() throws Exception {
		tx.commit();
		sess.close();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> createList(Class<T> type, String query) {
		List<T> list = sess.createQuery(query).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> createList(Class<T> type, String query, int firstResult,
			int resultSize) {
		List<T> list = sess.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(resultSize).list();
		return list;
	}

	public void delete(Object obj) {
		sess.delete(obj);
	}

	public int executeUpdate() {
		int row = sqlQuery.executeUpdate();
		sqlQuery = null;
		return row;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type, Serializable key) {
		return (T) sess.get(type, key);
	}

	public List<?> list() {
		List<?> list = sqlQuery.list();
		sqlQuery = null;
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> T load(Class<T> type, Serializable key) {
		return (T) sess.load(type, key);
	}

	public Serializable save(Object obj) {
		return sess.save(obj);
	}

	public HbnConn setFloat(String field) {
		sqlQuery.addScalar(field, StandardBasicTypes.FLOAT);
		return this;
	}

	public HbnConn setInt(String field) {
		sqlQuery.addScalar(field, StandardBasicTypes.INTEGER);
		return this;
	}

	public HbnConn setSQL(String query) {
		sqlQuery = sess.createSQLQuery(query);
		return this;
	}

	public HbnConn setString(String field) {
		sqlQuery.addScalar(field, StandardBasicTypes.STRING);
		return this;
	}

	public void update(Object obj) {
		sess.update(obj);
	}
}
