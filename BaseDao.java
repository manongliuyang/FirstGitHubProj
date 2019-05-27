import java.sql.*;

//数据库操作基类
public class BaseDao
{
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    //获取数据库连接
    public boolean getConnection()
    {

        try
        {
            //加载不同驱动厂商提供的驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取Connection连接
            String url = "jdbc:mysql://localhost:3306/jp_pro?characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "root");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    //增删改

    public int executeUpdate(String sql, Object[] params)
    {
        int updateRows = 0;
        if (this.getConnection())
        {
            try
            {
                pstmt = connection.prepareStatement(sql);
                //填充占位符
                for (int i = 0; i < params.length; i++)
                {
                    pstmt.setObject(i + 1, params[i]);
                }
                updateRows = pstmt.executeUpdate();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return updateRows;
    }


    //查
    public ResultSet executeSQL(String sql, Object[] params)
    {
        if (this.getConnection())
        {
            try
            {
                pstmt = connection.prepareStatement(sql);
                //填充占位符
                for (int i = 0; i < params.length; i++)
                {
                    pstmt.setObject(i + 1, params[i]);
                }
                rs = pstmt.executeQuery();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
        return rs;

    }
    //释放资源

    public boolean closeResource(){
        if (rs!=null){
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        if (pstmt!=null){
            try
            {
                pstmt.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        if (connection!=null){
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}