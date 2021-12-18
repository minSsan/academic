package db_store;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Store_Buy {
	public DB_Store_Buy() {
		DB_Store.TabPaymentTextArea.setText("");
	}

	public void search_table(String table_name, String model, int count) {
		String text_result = "";
		if (model.equals("")) {
			text_result = "-"+table_name+"-"+'\n'
				+ table_name +"model : 없음\t개수 : 0\t가격 : $0\n"+'\n';
			DB_Store.TabPaymentTextArea.setText(DB_Store.TabPaymentTextArea.getText() + text_result);
			return ;
		}
		try {
			String sqlStr = "";

			// 오라클 db 연결 끊기는 문제 발생 -> 누를 때마다 재로그인
			Class.forName("oracle.jdbc.OracleDriver");
			DB_Store.conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:XE", DB_Store_Login.username, DB_Store_Login.password
			);

			sqlStr = "SELECT model, price FROM "+ table_name +" WHERE model='"+model+"'";
			DB_Store.stmt = DB_Store.conn.prepareStatement(sqlStr);
			ResultSet rs = DB_Store.stmt.executeQuery();

			rs.next();

			text_result += "-"+table_name+"-\n"
				+ table_name +"model : "+rs.getString("model")+'\t'+"개수 : "+count+'\t'
				+"가격 : $"+(Integer.parseInt(rs.getString("price")) * count)+'\n'+'\n';
		} catch (Exception e) {
			System.out.println("DB_Store_"+table_name+"Exception: "+e);
			DB_Store.isCorrectModel = false;
		} finally {
			DB_Store.TabPaymentTextArea.setText(DB_Store.TabPaymentTextArea.getText() + text_result);
			try {
				if (DB_Store.stmt != null)
					DB_Store.stmt.close();
				if (DB_Store.conn != null)
					DB_Store.conn.close();
			} catch (SQLException e) {
			}
		}
	}
}
