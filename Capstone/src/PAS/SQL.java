package PAS;

import java.sql.*;

class SQL {
    private final String url = "jdbc:mysql://localhost:3306/capstone";
    private final String user = "root";
    private final String pass = "root";
    private ResultSet rs;


    public void insertQuery(String query)  { //insert data to table
        try {
        Connection con = DriverManager.getConnection(url, user, pass);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }

    }

    public boolean checkID(String id, String table) { //This checks if ID is existing
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT " + getPKColumn(table) + " FROM " + table + " WHERE " +
                    getPKColumn(table) + " = '" + id+"'");
            while (rs.next()) {
                if (rs.getString(1) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e);
        }
        return false;

    }

    public int countRow(String id, String table, String column) { //This counts the result set
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS recordCount FROM " + table + " WHERE " + column + " = '" + id+"'");
            rs.next();
            return rs.getInt("recordCount");


        } catch (Exception e) {
            System.out.println("Error: "+ e);
        }
        return 0;

    }

    public void updateTable(String ID,String table,String key,String column,String value){ //This deletes row from table
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            String SQL = "UPDATE "+table+" SET  "+column+" = '"+value+"' "+"WHERE "+key+" = '" + ID + "'";
            stmt.executeUpdate(SQL);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getTable(String id, String table,String key) { //Gets all data of the ID
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            String sql = ("SELECT * FROM " + table + " where " + key + " = '" + id + "';");
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            int rowNumbers = countRow(id, table, key);
            String[][] array = new String[rowNumbers][columnsNumber];
            int j=0;
            while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        array[j][i - 1] = rs.getString(i);
                    }
                    j++;
            }
            //System.out.println(Arrays.deepToString(array));
            return array;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPKColumn(String table) { //Gets primary key of table
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SHOW keys FROM "+table+" WHERE Key_name = 'PRIMARY'");
            rs.next();
            return rs.getString("Column_name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getID(String table,String argument) { //Gets ID of the table
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM "+table+" "+argument+";");
            rs.next();
            return rs.getString(getPKColumn(table));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkTable(String table) { //This checks if ID is existing
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + table+";");
            if(rs.next()){
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e);
        }
        return false;

    }

    Integer generateIntID(String table){
        String id;
        id= getID(table, "ORDER BY 1 DESC LIMIT 1");
        int incr = Integer.parseInt(id)+1;
        return incr;
    }


}
