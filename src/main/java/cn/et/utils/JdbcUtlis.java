package cn.et.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcUtlis {
    /**
     * 初始化jdbc的参数
     */
    private static String URL = null;
    private static String DRIVER_CLASS = null;
    private static String USERNAME = null;
    private static String PASSWORD = null;

    static Properties PRO_FILE = new Properties();
    static {
        try {
            PRO_FILE.load(JdbcUtlis.class.getResourceAsStream("/jdbc.properties"));
            URL = PRO_FILE.getProperty("url");
            DRIVER_CLASS = PRO_FILE.getProperty("driverClass");
            USERNAME = PRO_FILE.getProperty("username");
            PASSWORD = PRO_FILE.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Connection的方法
     */
    public static Connection getConn() throws Exception{
        Connection conn = null;
        // 加载驱动类
        Class.forName(DRIVER_CLASS);
        // 获取Connection
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }
    
    /**
     * 根据SQL语句返回以List<MAP>结构存放的行列数据
     * @param sql
     * @return
     * @throws Exception 
     */
    public static List<Map<String, String>> getDataBySql(String sql) throws Exception{
        Connection conn = getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        
        //获取结果集
        ResultSet rs = pst.executeQuery();
        
        //获取结果集元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        
        //获取列数量
        int cloumnCount = rsmd.getColumnCount();
        
        /*
         * list中的每个元素都是一个map(一行)，
         * 而map中的每个键值对就代表一列，结构是 列名 + 值。
         */
        
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        
        while (rs.next()) {
            Map<String, String> values = new HashMap<String, String>();
            for (int i = 1; i <= cloumnCount ; i++) {
                values.put(rsmd.getColumnName(i), rs.getString(i));
            }
            list.add(values);
        }
        return list;
    }
    
    /**
     * 执行DML语句
     * @param sql
     * @return
     * @throws Exception
     */
    public static int execute(String sql) throws Exception{
        Connection conn = getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        return pst.executeUpdate();
    }
}
