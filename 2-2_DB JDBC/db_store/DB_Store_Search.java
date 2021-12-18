package db_store;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class DB_Store_Search {
	public void search_table(String table_name, JTextArea text_area) {
		try {
			table_name = table_name.toUpperCase();
			String text_result = "";
			String[] columns;

			// 오라클 db 연결 끊기는 문제 발생 -> 누를 때마다 재로그인
			Class.forName("oracle.jdbc.OracleDriver");
			DB_Store.conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:XE", DB_Store_Login.username, DB_Store_Login.password
			);

			// column 종류를 알아내기 위해 column 수를 먼저 알아냄
			String sqlStr = "SELECT count(column_name) col_num from cols where table_name='"+table_name+"'";
			DB_Store.stmt = DB_Store.conn.prepareStatement(sqlStr);
			ResultSet rs = DB_Store.stmt.executeQuery(sqlStr);
			int col_num = 0;
			while(rs.next()) {
				col_num = rs.getInt("col_num");
			}
			columns = new String[col_num];
			// --------------------------------------------- //

			// column 이름을 배열에 모두 저장
			sqlStr = "SELECT column_name from cols where table_name='"+table_name+"'";
			DB_Store.stmt = DB_Store.conn.prepareStatement(sqlStr);
			rs = DB_Store.stmt.executeQuery(sqlStr);
			// --------------------------------------------- //

			// column 이름 & 밑줄 추가
			for (col_num = 0; rs.next(); col_num++) {
				columns[col_num] = rs.getString("column_name");
				text_result += columns[col_num] + '\t';
			}
			text_result += '\n';
			for (col_num = 0; col_num < columns.length; ++col_num) {
				text_result += "-------------";
			}
			text_result += '\n';
			// --------------------------------------------- //

			sqlStr = "SELECT * FROM "+table_name;
			DB_Store.stmt = DB_Store.conn.prepareStatement(sqlStr);
			rs = DB_Store.stmt.executeQuery(sqlStr);

			for (col_num = 0; rs.next(); ++col_num) {
				for (int i = 0; i < columns.length; ++i) {
					text_result += rs.getString(columns[i]).replaceAll(" ", "") + '\t';
				}
				text_result += '\n';
			}
			text_area.setText(text_result);

			rs.close();
			DB_Store.stmt.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
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
}
