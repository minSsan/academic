package db_store;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Store_Connect_Transaction {
	public void search_table(String table_name, String model, int count) {
		if (count == 0) return ;

		try {
			String sqlStr = "";

			// 오라클 db 연결 끊기는 문제 발생 -> 누를 때마다 재로그인
			Class.forName("oracle.jdbc.OracleDriver");
			DB_Store.conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:XE", DB_Store_Login.username, DB_Store_Login.password
			);

			sqlStr = "SELECT model, price FROM "+ table_name +" WHERE model='"+ model +"'";
			DB_Store.stmt = DB_Store.conn.prepareStatement(sqlStr);
			ResultSet rs = DB_Store.stmt.executeQuery();

			if (rs.next()) {
				int model_num = rs.getInt("model");
				int price = rs.getInt("price");

				DB_Store.total_price += price * count;

				sqlStr = "INSERT INTO transaction (TNUMBER, TMODEL, TCOUNT, TPRICE)"
					+"VALUES ("
					+"tnum_seq.NEXTVAL"+", "
					+model_num+", "
					+count+", "
					+(count * price)
					+")";
				System.out.println(sqlStr);
				DB_Store.stmt = DB_Store.conn.prepareStatement(sqlStr);
				DB_Store.stmt.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println("DB_Store_Connect_Transaction Exception: "+e);
		} finally {
			try {
				if (DB_Store.stmt != null)
					DB_Store.stmt.close();
				if (DB_Store.conn != null)
					DB_Store.conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public int calculate_total() {
		int total = 0;
		try {
			String sqlStr = "SELECT sum(tprice) FROM transaction";

			// 오라클 db 연결 끊기는 문제 발생 -> 누를 때마다 재로그인
			Class.forName("oracle.jdbc.OracleDriver");
			DB_Store.conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:XE", DB_Store_Login.username, DB_Store_Login.password
			);

			DB_Store.stmt = DB_Store.conn.prepareStatement(sqlStr);
			ResultSet rs = DB_Store.stmt.executeQuery();

			rs.next();

			total = rs.getInt("sum(tprice)");
		} catch (Exception e) {
			System.out.println("DB_Store_Connect_Transaction - calculate_total Exception: "+e);
		} finally {
			try {
				if (DB_Store.stmt != null)
					DB_Store.stmt.close();
				if (DB_Store.conn != null)
					DB_Store.conn.close();
			} catch (SQLException e) {
			}
		}
		return total;
	}
}
