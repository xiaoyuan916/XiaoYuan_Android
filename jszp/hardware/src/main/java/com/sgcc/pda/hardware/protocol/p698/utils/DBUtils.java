package com.sgcc.pda.hardware.protocol.p698.utils;



import com.sgcc.pda.hardware.protocol.p698.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;


public class DBUtils {
	private static Properties jdbcProperties;
	private static DataSource dataSource;

	public static Object lock = new Object();
	static {

	}

	// TODO: 2018/1/3 需要用我们自己的方式连接库
	private static void initPropDataSource() throws IOException {
		/*dataSource = (DataSource) ComponentFactory
				.getBean("dataSource");*/
		dataSource = null;
	}

	public DBUtils() {

	}

	public static void info(String msg) {
	//	LogHandler.info(LogHandler.DB, "【DBUtils】" + msg);
	}

	public static void error(String msg, Throwable t) {
	//	LogHandler.error(LogHandler.DB, "【DBUtils】" + msg, t);
	}

	/**
	 * 获取应用本身的数据库链接
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Connection getConnection() throws Exception {
		if (dataSource == null)
			DBUtils.initPropDataSource();
		/***********************************************************************
		 * BasicDataSource ds = (BasicDataSource) dataSource ; int numActive =
		 * ds.getNumActive(); //跟踪dmserver获得数据库连接的 有效活动连接数目
		 * info("当前的活动的连接数目:"+numActive);
		 **********************************************************************/
		// 记录获取连接的类名和方法名
		addConnMonitor();
		Connection conn =  dataSource.getConnection();
		conn.setAutoCommit(false);
		return conn ;
	}
	

	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception exception) {
			error("DBUtils.closeResultSet 关闭连接失败，失败原因：", exception);
			exception.printStackTrace();
		} finally {
			rs = null;
		}
	}

	public static void close(Statement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (Exception exception) {
			error("DBUtils.closeStatement 关闭连接失败，失败原因：", exception);
			exception.printStackTrace();
		} finally {
			pstmt = null;
		}
	}

	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();

			// 从记录中删除获取连接的类名和方法名
			removeConnMonitor();
		} catch (Exception exception) {
			error("DBUtils.closeConn 关闭连接失败，失败原因：", exception);
			exception.printStackTrace();
		} finally {
			conn = null;
		}
	}

	public static void close(Connection conn, Statement pstmt, ResultSet rs) {
		close(rs);
		close(pstmt);
		close(conn);
	}

	public static void close(Statement pstmt, ResultSet rs) {
		close(rs);
		close(pstmt);
	}

	public static Properties getJdbcProperties() {
		return jdbcProperties;
	}

	/**
	 * 根据SQL语句查询二维String数组，结构与结果集相同
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private static List<String[]> queryBySQL(Connection conn, String sqlStr,
                                             Object[] param) throws Exception {
		PreparedStatement pstmt = null;
		List<String[]> al = new ArrayList<String[]>();
		int colCnt = 0;
		ResultSet rs = null;
		StringBuffer params = new StringBuffer();
		try {
			pstmt = conn.prepareStatement(sqlStr);
			for (int i = 0; param != null && i < param.length; i++) {
				params.append(param[i]).append(";");
				// pstmt.setObject(i + 1, param[i]);
				setParameter(pstmt, i + 1, param[i]);
			}
			info("DBUtils.queryBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】begin ......");
			rs = pstmt.executeQuery();
			String[] arr = null;
			colCnt = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				arr = new String[colCnt];
				for (int i = 1; i <= colCnt; i++) {
					arr[i - 1] = rs.getString(i);
				}
				al.add(arr);
			}
			info("DBUtils.queryBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】end ......");
			return al;
		} catch (Exception e) {
			error("DBUtils.queryBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】 ......", e);
			throw e;
		} finally {
			DBUtils.close(pstmt, rs);
		}
	}
	/**
	 * 根据SQL语句查询二维String数组，结构与结果集相同

	 * @return
	 * @throws Exception
	 */
	public static List<String[]> queryBySQL(String sqlStr,
                                            Object[] param){
		Connection conn = null ;
		List<String[]> rlt =  null;
		try{
			conn = DBUtils.getConnection() ;
			rlt = queryBySQL(conn,sqlStr,param);
		}catch(Exception e){
			;
		}finally{
			DBUtils.close(conn) ;
		}
		return rlt ;
	}
	/**
	 * 根据SQL语句查询二维String数组，传入多组查询条件查询，结果集拼接成一个数组
	 * 
	 * @param conn
	 * @param sqlStr
	 * @return
	 * @throws Exception
	 */
	private static List<String[]> queryBatchBySQL(Connection conn,
                                                  String sqlStr, List<String[]> paramList) throws Exception {
		PreparedStatement pstmt = null;
		List<String[]> al = new ArrayList<String[]>();
		int colCnt = 0;
		ResultSet rs = null;
		StringBuffer params = new StringBuffer();
		try {
			pstmt = conn.prepareStatement(sqlStr);
			for (int plen = 0; plen < paramList.size(); plen++) {
				Object[] param = paramList.get(plen);
				for (int i = 0; param != null && i < param.length; i++) {
					params.append(param[i]).append(";");
					// pstmt.setObject(i + 1, param[i]);
					setParameter(pstmt, i + 1, param[i]);
				}

				info("DBUtils.queryBatchBySQL sql:【" + sqlStr + "】；param【"
						+ params + "】begin ......");
				params.delete(0, params.length());
				rs = pstmt.executeQuery();
				String[] arr = null;
				colCnt = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					arr = new String[colCnt];
					for (int i = 1; i <= colCnt; i++) {
						arr[i - 1] = rs.getString(i);
					}
					al.add(arr);
				}
				info("DBUtils.queryBySQL sql:【" + sqlStr + "】；param【" + params
						+ "】end ......");
				DBUtils.close(rs);
			}
			return al;
		} catch (Exception e) {
			error("DBUtils.queryBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】 ......", e);
			throw e;
		} finally {
			DBUtils.close(pstmt, rs);
		}
	}
	/**
	 * 根据SQL语句查询二维String数组，传入多组查询条件查询，结果集拼接成一个数组
	 *
	 * @param sqlStr
	 * @return
	 * @throws Exception
	 */	
	public static List<String[]> queryBatchBySQL(String sqlStr, List<String[]> paramList) {
		Connection conn = null ;
		List<String[]> rlt =  null;
		try{
			conn = DBUtils.getConnection() ;
			rlt = queryBatchBySQL(conn,sqlStr,paramList);
		}catch(Exception e){
			;
		}finally{
			DBUtils.close(conn) ;
		}
		return rlt ;
	}
	/**
	 * 根据SQL语句查询二维String数组，结构与结果集相同
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param param
	 * @param types  参见：java.sql.Types.
	 * @return
	 * @throws Exception
	 */
	private static List<String[]> queryBySQL(Connection conn, String sqlStr,
                                             Object[] param, Integer[] types) throws Exception {
		PreparedStatement pstmt = null;
		List<String[]> al = new ArrayList<String[]>();
		int colCnt = 0;
		ResultSet rs = null;
		StringBuffer params = new StringBuffer();

		if (param.length != types.length)
			throw new Exception("参数与参数类型个数不匹配！");

		try {
			pstmt = conn.prepareStatement(sqlStr);
			for (int i = 0; param != null && i < param.length; i++) {
				params.append(param[i]);
				// pstmt.setObject(i + 1, param[i],types[i]);
				setParameter(pstmt, i + 1, param[i], types[i]);
			}
			info("DBUtils.queryBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】 begin ......");
			rs = pstmt.executeQuery();
			String[] arr = null;
			ResultSetMetaData metaData = rs.getMetaData() ;
			colCnt = metaData.getColumnCount();
			while (rs.next()) {
				arr = new String[colCnt];
				for (int i = 1; i <= colCnt; i++) {
					
					arr[i - 1] = rs.getString(i);
				}
				al.add(arr);
			}
			info("DBUtils.queryBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】end ........");
			return al;
		} catch (Exception e) {
			error("DBUtils.queryBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】 ......", e);
			throw e;
		} finally {
			DBUtils.close(pstmt, rs);
		}
	}
	

	/**
	 * 根据SQL语句查询二维String数组，结构与结果集相同
	 *
	 * @param sqlStr
	 * @param param
	 * @param types  参见：java.sql.Types. 定义在java.sql.Types
	 * @return
	 * @throws Exception
	 */
	public static List<Map> queryBySQLRetMap(String sqlStr,
                                             Object[] param, Integer[] types) throws Exception {
		Connection conn = null ;
		PreparedStatement pstmt = null;
		List<Map> al = new ArrayList<Map>();
		int colCnt = 0;
		ResultSet rs = null;
		StringBuffer params = new StringBuffer();

		if (param.length != types.length)
			throw new Exception("参数与参数类型个数不匹配！");
		Map<String,String> arr = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sqlStr);
			for (int i = 0; param != null && i < param.length; i++) {
				params.append(param[i]).append(";");
				// pstmt.setObject(i + 1, param[i],types[i]);
				setParameter(pstmt, i + 1, param[i], types[i]);
			}
			info("DBUtils.queryBySQLRetMap sql:【" + sqlStr + "】；param【" + params
					+ "】 begin ......");
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData() ;
			colCnt = metaData.getColumnCount();
			String columnName = "";
			String tempValue = "";
			int typeId = 0;
			while (rs.next()) {
				arr = new HashMap<String,String>();
				for (int i = 1; i <= colCnt; i++) {
					typeId = rs.getMetaData().getColumnType(i);
					columnName = rs.getMetaData().getColumnName(i).toUpperCase();
					tempValue = rs.getString(i);
					if(tempValue == null){
						tempValue = "" ;
					}
					if(typeId == Types.FLOAT || typeId == Types.DOUBLE || typeId == Types.NUMERIC || typeId == Types.DECIMAL)
					{
						if(StringUtils.isNotNull(tempValue) && tempValue.startsWith("."))
						{
							tempValue = "0"+tempValue;
						}else if(StringUtils.isNotNull(tempValue) &&  tempValue.startsWith("-."))
						{
							tempValue = "-" + "0" + tempValue.substring(1);
						}
					}
					arr.put(columnName, tempValue);
					
				}
				al.add(arr);
			}
			info("DBUtils.queryBySQLRetMap end ........");
			return al;
		} catch (Exception e) {
			error("DBUtils.queryBySQLRetMap sql:【" + sqlStr + "】；param【" + params
					+ "】 ......", e);
			throw e;
		} finally {
			DBUtils.close(conn,pstmt, rs);
		}
	}
	/**
	 * 根据SQL语句查询二维String数组，结构与结果集相同
	 *
	 * @param sqlStr
	 * @param param
	 * @param types  参见：java.sql.Types.
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> queryBySQL(String sqlStr,
                                            Object[] param, Integer[] types){
		Connection conn = null ;
		List<String[]> rlt =  null;
		try{
			conn = DBUtils.getConnection() ;
			rlt = queryBySQL(conn,sqlStr,param,types);
		}catch(Exception e){
			;
		}finally{
			DBUtils.close(conn) ;
		}
		return rlt ;
	}

	/**
	 * 根据sql查询数据，组织成二维数组，不需要外界connection
	 *
	 * @param queryCause
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> queryBySQL(String queryCause)
			throws Exception {
		Connection conn = null ;
		List<String[]> al = new ArrayList<String[]>();
		int colCnt = 0;
		Statement smt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection() ;
			smt = conn.createStatement();
			info("DBUtils.queryBySQL sql:【" + queryCause + "】；begin ......");
			rs = smt.executeQuery(queryCause);
			String[] arr = null;
			int typeId = 0;
			String tempValue = "";
			colCnt = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				arr = new String[colCnt];
				for (int i = 1; i <= colCnt; i++) {
					typeId = rs.getMetaData().getColumnType(i);
					tempValue = rs.getString(i);
					if(tempValue == null){
						tempValue = "" ;
					}
					if(typeId == Types.FLOAT || typeId == Types.DOUBLE || typeId == Types.NUMERIC || typeId == Types.DECIMAL)
					{
						if(StringUtils.isNotNull(tempValue) && tempValue.startsWith("."))
						{
							tempValue = "0"+tempValue;
						}else if(StringUtils.isNotNull(tempValue) &&  tempValue.startsWith("-."))
						{
							tempValue = "-" + "0" + tempValue.substring(1);
						}
					}
					arr[i - 1] = tempValue;
				}
				al.add(arr);
			}
			info("DBUtils.queryBySQL sql:【" + queryCause + "】；end .......");
			return al;
		} catch (Exception e) {
			error("DBUtils.queryBySQL sql:【" + queryCause + "】；  ......", e);
			throw e;
		} finally {
			DBUtils.close(conn,smt, rs);
		}
	}
	
//	/**
//	 * 执行查询语句 返回 一个LIST<Map>
//	 */
//	public static List<Map> selectAnySql(String selectSql)
//	{
//		List<Map> retList = null;
//        if (selectSql != null && !"".equals(selectSql))
//        {
//            Queryer query = new Queryer();
//            query.setDataSource(dataSource);
//            query.setSql(selectSql);
//            query.compile();
//            retList = query.execute();
//            info("selectAnySql执行的SQL=: " + selectSql);
//        }
//        return retList;
//	}
	
	public static List<Map> queryBySQLRetMap(String queryCause) throws Exception
	{
		Connection conn = null ;
		List<Map> al = new ArrayList<Map>();
		int colCnt = 0;
		Statement smt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection() ;
			smt = conn.createStatement();
			info("DBUtils.queryBySQLRetMap sql:【" + queryCause + "】；begin ......");
			rs = smt.executeQuery(queryCause);
			Map<String,String> arr = null;
			int typeId = 0;
			String columnName = "";
			String tempValue = "";
			colCnt = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				arr = new HashMap<String,String>();
				for (int i = 1; i <= colCnt; i++) {
					typeId = rs.getMetaData().getColumnType(i);
					columnName = rs.getMetaData().getColumnName(i).toUpperCase();
					tempValue = rs.getString(i);
					if(tempValue == null){
						tempValue = "" ;
					}
					if(typeId == Types.FLOAT || typeId == Types.DOUBLE || typeId == Types.NUMERIC || typeId == Types.DECIMAL)
					{
						if(StringUtils.isNotNull(tempValue) && tempValue.startsWith("."))
						{
							tempValue = "0"+tempValue;
						}else if(StringUtils.isNotNull(tempValue) &&  tempValue.startsWith("-."))
						{
							tempValue = "-" + "0" + tempValue.substring(1);
						}
					}
					arr.put(columnName, tempValue);
				}
				al.add(arr);
			}
			info("DBUtils.queryBySQLRetMap sql:【" + queryCause + "】；end .......");
			return al;
		} catch (Exception e) {
			error("DBUtils.queryBySQLRetMap sql:【" + queryCause + "】；  ......", e);
			throw e;
		} finally {
			DBUtils.close(conn,smt,rs);
		}
		
	}
	
	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 * 
	 * @param conn
	 * @param queryCause
	 * @throws Exception
	 */
	public static void updateBySQL(Connection conn, String queryCause)
			throws Exception {

		Statement smt = null;
		try {
			smt = conn.createStatement();
			info("DBUtils.updateBySQL sql:【" + queryCause + "】；begin ......");
			smt.execute(queryCause);
			info("DBUtils.updateBySQL sql:【" + queryCause + "】；end ......");
		} catch (Exception e) {
			error("DBUtils.updateBySQL sql:【" + queryCause + "】； ......", e);
			throw e;
		} finally {
			DBUtils.close(smt);
		}
	}

	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param param
	 * @throws Exception
	 */
	public static void updateBySQL(Connection conn, String sqlStr,
                                   Object[] param) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer params = new StringBuffer();
		try {
			pstmt = conn.prepareStatement(sqlStr);
			for (int i = 0; param != null && i < param.length; i++) {
				params.append(param[i]).append("; ");
				// pstmt.setObject(i + 1, param[i]);
				setParameter(pstmt, i + 1, param[i]);
			}
			info("DBUtils.updateBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】begin .....");
			pstmt.executeUpdate();
			info("DBUtils.updateBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】 end .....");
		} catch (Exception e) {
			error("DBUtils.updateBySQL sql:【" + sqlStr + "】； ......", e);
			throw e;
			// e.printStackTrace();
		} finally {
			DBUtils.close(pstmt);
		}
	}
    
	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 * 
	 * @param sqlStr
	 * @param param
	 * @throws Exception
	 */
	public static void updateBySQL(String sqlStr,
			Object[] param) throws Exception {
		Connection conn = null ;
		try {
			conn = getConnection();
			updateBySQL(conn, sqlStr, param) ;
			conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param param
	 * @param types  参见：java.sql.Types.
	 * @throws Exception
	 */
	public static void updateBySQL(Connection conn, String sqlStr,
                                   Object[] param, Integer[] types) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer params = new StringBuffer();
		try {
			pstmt = conn.prepareStatement(sqlStr);
			for (int i = 0; param != null && i < param.length; i++) {
				if (param.length != types.length)
					throw new Exception("参数与参数类型个数不匹配！");
				params.append(param[i]).append("; ");
				// pstmt.setObject(i + 1, param[i],types[i]);
				setParameter(pstmt, i + 1, param[i], types[i]);
			}
			info("DBUtils.updateBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】 begin .....");
			pstmt.executeUpdate();
			info("DBUtils.updateBySQL sql:【" + sqlStr + "】；param【" + params
					+ "】 end .....");
		} catch (Exception e) {
			error("DBUtils.updateBySQL sql:【" + sqlStr + "】； ......", e);
			throw e;
		} finally {
			DBUtils.close(pstmt);
		}
	}
	

	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 *
	 * @param sqlStr
	 * @param param
	 * @param types  参见：java.sql.Types.
	 * @throws Exception
	 */
	public static void updateBySQL(String sqlStr,
                                   Object[] param, Integer[] types) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			updateBySQL(conn,sqlStr, param, types) ;
			conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	
	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param paramList
	 * @throws Exception
	 */
	public static void batchUpdateBySQL(Connection conn, String sqlStr,
                                        List<Object[]> paramList) throws Exception {

		PreparedStatement pstmt = null;
		StringBuffer params = new StringBuffer();
		try {
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】；begin .....");
			pstmt = conn.prepareStatement(sqlStr);
			for (Object[] param : paramList) {
				for (int i = 0; param != null && i < param.length; i++) {
					params.append(param[i]).append("; ");
					// if(param[i] == null)
					// param[i] = "" ;
					// pstmt.setObject(i + 1, param[i]);
					setParameter(pstmt, i + 1, param[i]);
				}
				info("param【" + params + "】");
				params.delete(0, params.length());
				pstmt.addBatch();
			}

			pstmt.executeBatch();
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】； end .....");
		} catch (Exception e) {
			error("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】； ......", e);
			throw e;
		} finally {
			DBUtils.close(pstmt);
		}
	}


	
	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 *
	 * @param sqlStr
	 * @param paramList
	 * @throws Exception
	 */
	public static void batchUpdateBySQL( String sqlStr,
			List<Object[]> paramList) throws Exception {
		Connection conn = null ;
		try {
			conn = getConnection();
			batchUpdateBySQL(conn, sqlStr, paramList);
			conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}
	
	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param keyCase
	 * @param paramList
	 * @throws Exception
	 */
	public static void batchUpdateBySQL(Connection conn, String sqlStr,
                                        int[] keyCase, List<Object[]> paramList) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer params = new StringBuffer();
		try {
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】；begin .....");
			pstmt = conn.prepareStatement(sqlStr);
			for (Object[] param : paramList) {
				for (int i = 0; param != null && i < keyCase.length; i++) {
					params.append(param[keyCase[i]]).append("; ");
					// if(param[i] == null)
					// param[i] = "" ;
					// pstmt.setObject(keyCase[i], param[i]);
					setParameter(pstmt, i + 1, param[i]);
				}
				info("param【" + params + "】");
				params.delete(0, params.length());
				pstmt.addBatch();
			}

			pstmt.executeBatch();
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】； end .....");
		} catch (Exception e) {
			error("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】； ......", e);
			throw e;
		} finally {
			DBUtils.close(pstmt);
		}
	}

	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 *
	 * @param sqlStr
	 * @param keyCase
	 * @param paramList
	 * @throws Exception
	 */
	public static void batchUpdateBySQL( String sqlStr,
			int[] keyCase, List<Object[]> paramList) throws Exception {
		Connection conn = null ;
		try {
			conn = getConnection();
			batchUpdateBySQL(conn,sqlStr,keyCase,paramList);
			conn.commit();
		}catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}
	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param paramList
	 * @param types  参见：java.sql.Types.
	 * @throws Exception
	 */
	public static void batchUpdateBySQL(Connection conn, String sqlStr,
                                        List<Object[]> paramList, Integer[] types) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer params = new StringBuffer();
		try {
			pstmt = conn.prepareStatement(sqlStr);
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】 begin .....");
			for (Object[] param : paramList) {
				if (param.length != types.length)
					throw new Exception("参数与参数类型个数不匹配！");
				for (int i = 0; param != null && i < param.length; i++) {
					params.append(param[i]).append("；");
					// pstmt.setObject(i + 1, param[i], types[i]);
					setParameter(pstmt, i + 1, param[i], types[i]);
				}
				info("param【" + params + "】");
				params.delete(0, params.length());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】 end .....");
		} catch (Exception e) {
			error("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】； ......", e);
			throw e;
		} finally {
			DBUtils.close(pstmt);
		}
	}

	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 *
	 * @param sqlStr
	 * @param paramList
	 * @param types  参见：java.sql.Types.
	 * @throws Exception
	 */
	public static void batchUpdateBySQL(String sqlStr,
                                        List<Object[]> paramList, Integer[] types) throws Exception {
		Connection conn = null ;
		try {
			conn = getConnection();
			batchUpdateBySQL(conn,sqlStr, paramList, types) ;
			conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}
	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 * 
	 * @param conn
	 * @param sqlStr
	 *            sql
	 * @param keyCase
	 *            对应的key值
	 * @param paramList
	 *            要处理的结果集
	 * @param types  参见：java.sql.Types.
	 *            类型
	 * @throws Exception
	 */
	public static void batchUpdateBySQL(Connection conn, String sqlStr,
                                        int[] keyCase, List<Object[]> paramList, Integer[] types)
			throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer params = new StringBuffer();
		try {
			pstmt = conn.prepareStatement(sqlStr);
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】 begin .....");
			for (Object[] param : paramList) {
				if (param.length != types.length)
					throw new Exception("参数与参数类型个数不匹配！");
				for (int i = 0; param != null && i < keyCase.length; i++) {
					params.append(param[keyCase[i]]).append("；");
					// pstmt.setObject(keyCase[i], param[keyCase[i]], types[i]);
					setParameter(pstmt, i + 1, param[i], types[i]);
				}
				info("param【" + params + "】");
				params.delete(0, params.length());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】 end .....");
		} catch (Exception e) {
			info("DBUtils.batchUpdateBySQL sql:【" + sqlStr + "】 end .....");
			throw e;
		} finally {
			DBUtils.close(pstmt);
		}
	}

	/**
	 * 根据sql更新数据库，做insert 或 update，有更新条件
	 *
	 * @param sqlStr
	 *            sql
	 * @param keyCase
	 *            对应的key值
	 * @param paramList
	 *            要处理的结果集
	 * @param types  参见：java.sql.Types.
	 *            类型
	 * @throws Exception
	 */
	public static void batchUpdateBySQL(String sqlStr,
                                        int[] keyCase, List<Object[]> paramList, Integer[] types)
			throws Exception {
		Connection conn = null ;
		try {
			conn = getConnection();
			batchUpdateBySQL(conn,sqlStr,keyCase,paramList,types);
			conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 校验链接是否可用
	 * 
	 * @param conn
	 * @return
	 */
	public static boolean checkConnEnable(Connection conn) {
		try {
//			DBUtils.queryBySQL(conn, DBUtils.CHECK_CONN_ENABLE_SQL);
		} catch (Exception e) {
			error("检验数据库链接，链接不可用，异常信息如下：", e);
			return false;
		}
		return true;
	}

	public static void beginTrans(Connection conn) {
		try {
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			error("事务开始失败", e);
		}
	}

	public static void rollTrans(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
			e.printStackTrace();
			error("事务回滚失败", e);
		} finally {
			close(conn);
		}
	}

	public static void commitTrans(Connection conn) {
		try {
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			error("事务回滚失败", e);
		} finally {
			close(conn);
		}
	}

	/**
	 * 获取分页查询sql
	 * 
	 * @param srcSql
	 * @return
	 */
	public static String getPageResultSQL(String srcSql, int begin, int end) {
		return " select * from (  "
				+ srcSql
				+ " ) temp_page_table limit  " 
				+ begin + end
				+ " , " + (begin - end) + "  ";
	}

	/**
	 * 获取分页查询sql
	 * 
	 * @param srcSql
	 * @return
	 */
	public static String getPageCountSQL(String srcSql) {
		return " select count(1) CONT from (" + srcSql + ") PAGE_COUNT_GATHER";
	}
	/**
	 * 内部使用
	 * 获取查询结果中的总数列，必须与getPageCountSQL中的列别名一致：CONT
	 * @param rlt
	 * @return
	 */
	private static int getPageCountFromRlt(List rlt){
		if(rlt.size() > 0){
			Object obj = rlt.get(0);
			if(obj instanceof Map){
				Map m = (Map)obj;
				String count = (String)m.get("CONT") ;
				return Integer.parseInt(count);
			}else if(obj instanceof String[]){
				String count = ((String[])obj)[0] ;
				return Integer.parseInt(count);
			}else if(obj instanceof String){
				String count = (String) obj ;
				return Integer.parseInt(count);
			}else if(obj instanceof Integer){
				return (Integer) obj ;
			}
		}
		return 0 ;
	}
	/**
	 * 获取总数
	 * @param sqlStr
	 * @param param
	 * @param types  参见：java.sql.Types.
	 * @return
	 * @throws Exception
	 */
	public static int getTatal(String sqlStr,
                               Object[] param, Integer[] types) throws Exception {
		String sql = getPageCountSQL(sqlStr);
		List l = queryBySQLRetMap(  sql,
				 param,  types);
		return getPageCountFromRlt(l);
	}

	/**
	 * 获取总数
	 * @param sqlStr
	 * @return
	 * @throws Exception
	 */
	public static int getTatal( String sqlStr) throws Exception {
		String sql = getPageCountSQL(sqlStr);
		List l = queryBySQLRetMap( sql);
		return getPageCountFromRlt(l);
	}
	

	
	
	/**
	 * 为psmtmt设置参数
	 *
	 * @param value
	 * @param type
	 * @throws SQLException
	 */
	private static PreparedStatement setParameter(PreparedStatement pstmt,
                                                  int index, Object value, int type) throws SQLException {
		if (value == null) {
			if (type == Types.NULL)
				pstmt.setObject(index, value, type);
			else
				pstmt.setNull(index, type);
		} else {
			pstmt.setObject(index, value, type);
		}
		return pstmt;
	}

	/**
	 * 为psmtmt,stmt设置参数
	 *
	 * @param value
	 * @throws SQLException
	 */
	private static PreparedStatement setParameter(PreparedStatement pstmt,
                                                  int index, Object value) throws SQLException {
		if (value == null) {
//			pstmt.setObject(index, "");
			pstmt.setObject(index, null); // mod by zhangwei@2015年5月5日 基于MySql修改此功能，由原来的空字符串修改为Null
		} else {
			pstmt.setObject(index, value);
		}
		return pstmt;
	}

	/**
	 * 为psmtmt设置参数
	 *
	 * @param value
	 * @param type
	 * @throws SQLException
	 */
	private static PreparedStatement setParameters(PreparedStatement pstmt,
                                                   int index, Object[] value, int[] type) throws SQLException {
		if (value == null) {
			;
		} else {
			if (value.length != type.length) {
				throw new SQLException("参数与类别长度不一致！");
			}
			for (int i = 0; i < value.length; i++) {
				setParameter(pstmt, index, value[i], type[i]);
			}
		}
		return pstmt;
	}

	/**
	 * 为psmtmt设置参数
	 *
	 * @param value
	 * @throws SQLException
	 */
	private static PreparedStatement setParameters(PreparedStatement pstmt,
                                                   int index, Object[] value) throws SQLException {
		if (value == null) {
			;
		} else {
			for (int i = 0; i < value.length; i++) {
				setParameter(pstmt, index, value[i]);
			}
		}
		return pstmt;
	}

	/**
	 * 为psmtmt设置参数
	 *
	 * @param value
	 * @param type
	 * @throws SQLException
	 */
	private static PreparedStatement setParameters(PreparedStatement pstmt,
                                                   int index, List<Object[]> value, int[] type) throws SQLException {
		if (value == null || value.size() == 0) {
			;
		} else {
			for (int i = 0; i < value.size(); i++) {
				setParameters(pstmt, index, value.get(i), type);
			}
		}
		return pstmt;
	}

	/**
	 * 为psmtmt设置参数
	 *
	 * @param value
	 * @throws SQLException
	 */
	private static PreparedStatement setParameters(PreparedStatement pstmt,
                                                   int index, List<Object[]> value) throws SQLException {
		if (value == null || value.size() == 0) {
			;
		} else {
			for (int i = 0; i < value.size(); i++) {
				setParameters(pstmt, index, value.get(i));
			}
		}
		return pstmt;
	}

	/**
	 * 参数转换为字符串
	 * 
	 * @param params
	 * @return
	 */
	private String paramsToString(Object[] params) {
		// Dialect
		StringBuffer sbf = new StringBuffer();
		for (Object o : params) {
			sbf.append(o).append(";");
		}
		return sbf.toString();
	}

	/**
	 * 记录获取连接的类、方法及次数
	 */
	private static void addConnMonitor() {
		// 记录获取连接的类名和方法名
//		StackTraceElement[] stack = (new Throwable()).getStackTrace();
//		synchronized (lock) {
//			for (StackTraceElement ste : stack) {
//				if (ste.getClassName() != DBUtils.class.getName()) {
//					String key = ste.getClassName() + "." + ste.getMethodName()
//							+ "()";
//					int flag = 0;
//					for (Entry<String, Integer> entry : connCach.entrySet()) {
//						if (key.equals(entry.getKey())) {
//							int value = entry.getValue();
//							connCach.put(key, value + 1);
//							flag = 1;
//						}
//					}
//					if (flag == 0) {
//						connCach.put(key, 1);
//					}
//					break;
//				}
//			}
//		}
	}

	public static String clobToString(Clob c) throws Exception {
		BufferedReader br = new BufferedReader(c.getCharacterStream());
		String s = br.readLine() ;
		StringBuffer sbf = new StringBuffer();
		while(s != null){
			sbf.append(s) ;
			s = br.readLine() ;
		}
		return sbf.toString() ;
	}
//	/**
//	 * 以事务方式处理所有更新删除插入操作
//	 */
//	public static void doTransAction(DBTransaction ts) throws Exception{
//		Connection conn = null ;
//		boolean isAutoCommit = false ;
//		try {
//			conn = DBUtils.getConnection() ;
//			isAutoCommit = conn.getAutoCommit() ;
//			conn.setAutoCommit(false) ;
//			ts.exec(conn) ;
//			conn.commit() ;
//			conn.setAutoCommit(isAutoCommit) ;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			throw e ;
//		}finally{
//			DBUtils.close(conn) ;
//		}
//	}
	/**
	 * 从记录中删除获取连接的类、方法或减少次数
	 */
	private static void removeConnMonitor() {
//		StackTraceElement[] stack = (new Throwable()).getStackTrace();
//		synchronized (lock) {
//			for (StackTraceElement ste : stack) {
//				if (ste.getClassName() != DBUtils.class.getName()) {
//					for (Entry<String, Integer> entry : connCach.entrySet()) {
//						String key = ste.getClassName() + "."
//								+ ste.getMethodName() + "()";
//						int value = entry.getValue();
//						if (key.equals(entry.getKey())) {
//							if (value != 1) {
//								connCach.put(entry.getKey(), value - 1);
//							} else {
//								connCach.remove(key);
//							}
//							break;
//						}
//					}
//					break;
//				}
//			}
//		}
	}

	private static final String CHECK_CONN_ENABLE_SQL = "select 1 from dual";
	
	
	/**
	 * 查询BLOB字段
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Blob queryBlobBySQL(Connection conn, String sqlStr,
                                      Object[] param) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Blob blob = null;
		StringBuffer params = new StringBuffer();
		try {
			pstmt = conn.prepareStatement(sqlStr);
			for (int i = 0; param != null && i < param.length; i++) {
				params.append(param[i]).append(";");
				setParameter(pstmt,i + 1, param[i]);
			}
			info( "DBUtils.queryBySQL sql:【" + sqlStr
					+ "】；param【" + params + "】begin ......");
			rs = pstmt.executeQuery();
			info( "DBUtils.queryBySQL sql:【" + sqlStr
					+ "】；param【" + params + "】end ......");
			while(rs.next()) {
				if(rs.isFirst()) {
					blob = rs.getBlob(1);
				}
			}
			return blob;
		} catch (Exception e) {
			error( "DBUtils.queryBySQL sql:【" + sqlStr
					+ "】；param【" + params + "】 ......",e);
			throw e;
		} finally {
			DBUtils.close(pstmt, rs);
		}
	}
}
