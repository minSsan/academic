package db_store;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

public class DB_Store_Login {

	static String username;
	static String password;

	private boolean is_correct = false;

	public void login() {
		while(!is_correct) {
			username = JOptionPane.showInputDialog(null, "Oracle 아이디를 입력해주세요", "입력", JOptionPane.QUESTION_MESSAGE);
			password = JOptionPane.showInputDialog(null, "Oracle 비밀번호를 입력해주세요", "입력", JOptionPane.QUESTION_MESSAGE);
			connectDB();
		}
	}

	private void connectDB() {
		try {
			DB_Store gui = new DB_Store();
			Class.forName("oracle.jdbc.OracleDriver");
			DB_Store.conn = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", username, password);
			// 로그인 되었습니다 창 띄우기
			JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
			is_correct = true;
			gui.setVisible(true);
		} catch (SQLException e) {
			// 로그인할 수가 없습니다 창 띄우기
			JOptionPane.showMessageDialog(null, "로그인 할 수 없습니다. 아이디와 비밀번호를 다시 입력해주세요");
			System.out.println("SQLException: " + e);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}
