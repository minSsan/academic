package db_store;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class DB_Store extends JFrame implements ActionListener {
	static Connection conn;
	static PreparedStatement stmt;

	static boolean isCorrectModel = true;

	static int total_price = 0;

	private String pc_model = "";
	private String laptop_model = "";
	private String printer_model = "";
	private int pc_model_count = 0;
	private int laptop_model_count = 0;
	private int printer_model_count = 0;

	JPanel MainPanel;
	// 탭 패널(조회, 최종구매, 구매내역)
	JTabbedPane TabbedPanel;

	// ---------------------- 타이틀 라벨 ----------------------//
	JLabel LeftTitle;
	JLabel RightTitle;
	TitledBorder tBorder;
	// ------------------------------------------------------ //

	// ---------------------- 구매 패널 ----------------------- //
	JPanel BuyPanel;

	JLabel BuyPanelLabel_ModelNum; 	// 모델 번호
	JLabel BuyPanelLabel_Count;		// 개수

	JLabel BuyPanelLabel_PC;		// PC
	JLabel BuyPanelLabel_Laptop;	// Laptop
	JLabel BuyPanelLabel_Printer;	// Printer

	JTextField PCModelInput;
	JTextField LaptopModelInput;
	JTextField PrinterModelInput;

	JComboBox<Integer> PCCountCombobox;
	JComboBox<Integer> LaptopCountCombobox;
	JComboBox<Integer> PrinterCountCombobox;

	JButton BuyPanelBtn_Buy;		// 구매 버튼
	JButton BuyPanelBtn_Exit;		// 닫기 버튼
	// ------------------------------------------------------ //

	// ------------------ 조회 패널 (Search) ------------------ //
	JLabel TabSearchLabel;
	JComboBox<String> TabSearchCombobox;
	static JTextArea TabSearchTextArea;
	// ------------------------------------------------------ //

	// -------------- 최종 구매 패널 (Payment) ---------------- //
	static JTextArea TabPaymentTextArea;
	JButton TabPaymentBtn_Buy;
	JButton TabPaymentBtn_Reset;
	// ----------------------------------------------------- //

	// -------------- 구매 내역 패널 (Note) --------------- //
	static JTextArea TabNoteTextArea;
	JLabel TabNoteLabel_totalTitle;
	JLabel TabNoteLabel_totalCount;
	JButton TabNoteBtn_Show;
	// ---------------------------------------------------- //

	public DB_Store() {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		setTitle("JDBC와 자바 GUI 실습"); // 상단위에 타이틀바
		setSize(940, 430); // 전체 크기
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		MainPanel = new JPanel();
		MainPanel.setLayout(null);

		makeComponent();

		getContentPane().add(MainPanel, BorderLayout.CENTER);
	}

	public void makeComponent() {
		// ---------------------- 타이틀 ------------------------ //
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 / MM월 / dd일");
		String date = LocalDate.now().format(formatter);
		LeftTitle = new JLabel("DB 컴퓨터가게", JLabel.CENTER);
		RightTitle = new JLabel("좋은 시간 되세요. ["+date+"]", JLabel.CENTER);

		tBorder = new TitledBorder(new LineBorder(Color.BLACK, 2));

		LeftTitle.setBorder(tBorder);
		LeftTitle.setFont(new Font(null, Font.BOLD, 25));
		LeftTitle.setBounds(10, 10, 380, 60);

		RightTitle.setBorder(tBorder);
		RightTitle.setFont(new Font(null, Font.BOLD, 25));
		RightTitle.setBounds(410, 10, 500, 60);

		MainPanel.add(LeftTitle);
		MainPanel.add(RightTitle);
		// ----------------------------------------------------- //

		// ----------------------- 구매 탭 ---------------------- //
		BuyPanel = new JPanel();
		BuyPanel.setLayout(null);
		BuyPanel.setBorder(new TitledBorder("구매"));
		BuyPanel.setBounds(10, 80, 380, 300);

		BuyPanelLabel_ModelNum = new JLabel("모델 번호", JLabel.CENTER);
		BuyPanelLabel_ModelNum.setBounds(140, 40, 80, 20);
		BuyPanel.add(BuyPanelLabel_ModelNum);

		BuyPanelLabel_Count = new JLabel("개수", JLabel.CENTER);
		BuyPanelLabel_Count.setBounds(270, 40, 80, 20);
		BuyPanel.add(BuyPanelLabel_Count);

		// PC
		BuyPanelLabel_PC = new JLabel();
		BuyPanelLabel_PC.setText("PC");
		BuyPanelLabel_PC.setBounds(30, 70, 150, 40);
		BuyPanel.add(BuyPanelLabel_PC);

		PCModelInput = new JTextField();
		PCModelInput.setBounds(140, 70, 80, 40);
		BuyPanel.add(PCModelInput);

		PCCountCombobox = new JComboBox<Integer>();
		PCCountCombobox.setBounds(270, 70, 80, 40);
		for (int i = 1; i < 11; ++i) PCCountCombobox.addItem(i);
		BuyPanel.add(PCCountCombobox);

		// Laptop
		BuyPanelLabel_Laptop = new JLabel();
		BuyPanelLabel_Laptop.setText("Laptop");
		BuyPanelLabel_Laptop.setBounds(30, 120, 150, 40);
		BuyPanel.add(BuyPanelLabel_Laptop);

		LaptopModelInput = new JTextField();
		LaptopModelInput.setBounds(140, 120, 80, 40);
		BuyPanel.add(LaptopModelInput);

		LaptopCountCombobox = new JComboBox<Integer>();
		LaptopCountCombobox.setBounds(270, 120, 80, 40);
		for (int i = 1; i < 11; ++i) LaptopCountCombobox.addItem(i);
		BuyPanel.add(LaptopCountCombobox);

		// Printer
		BuyPanelLabel_Printer = new JLabel();
		BuyPanelLabel_Printer.setText("Printer");
		BuyPanelLabel_Printer.setBounds(30, 170, 150, 40);
		BuyPanel.add(BuyPanelLabel_Printer);

		PrinterModelInput = new JTextField();
		PrinterModelInput.setBounds(140, 170, 80, 40);
		BuyPanel.add(PrinterModelInput);

		PrinterCountCombobox = new JComboBox<Integer>();
		PrinterCountCombobox.setBounds(270, 170, 80, 40);
		for (int i = 1; i < 11; ++i) PrinterCountCombobox.addItem(i);
		BuyPanel.add(PrinterCountCombobox);

		// 구매 버튼
		BuyPanelBtn_Buy = new JButton("구매");
		BuyPanelBtn_Buy.setBounds(100, 240, 120, 40);
		BuyPanelBtn_Buy.addActionListener(this);
		BuyPanel.add(BuyPanelBtn_Buy);

		// 닫기 버튼
		BuyPanelBtn_Exit = new JButton("닫기");
		BuyPanelBtn_Exit.setBounds(230, 240, 120, 40);
		BuyPanelBtn_Exit.addActionListener(this);
		BuyPanel.add(BuyPanelBtn_Exit);

		MainPanel.add(BuyPanel);
		// ----------------------------------------------------- //

		// ----------------------- 탭 바 ------------------------ //
		TabbedPanel = new JTabbedPane();

		JPanel TabbedPanel_Search = new JPanel(); // 조회 탭 패널
		JPanel TabbedPanel_Payment = new JPanel(); // 최종 구매 탭 패널
		JPanel TabbedPanel_Note = new JPanel(); // 구매 내역 탭 패널
		TabbedPanel_Search.setLayout(null);
		TabbedPanel_Payment.setLayout(null);
		TabbedPanel_Note.setLayout(null);

		TabbedPanel.addTab("조회",TabbedPanel_Search);
		TabbedPanel.addTab("최종 구매",TabbedPanel_Payment);
		TabbedPanel.addTab("구매 내역",TabbedPanel_Note);

		TabbedPanel.setBounds(410, 80, 500, 300);
		MainPanel.add(TabbedPanel);
		// ----------------------------------------------------- //

		// --------------------- 조회 패널 ---------------------- //
		TabSearchLabel = new JLabel();
		TabSearchLabel.setText("조회할 물품 선택");
		TabSearchLabel.setIcon(new ImageIcon(""));
		TabSearchLabel.setBounds(20, 0, 150, 80);
		TabbedPanel_Search.add(TabSearchLabel);

		TabSearchCombobox = new JComboBox<String>();
		TabSearchCombobox.addItem("PC");
		TabSearchCombobox.addItem("Laptop");
		TabSearchCombobox.addItem("Printer");

		TabSearchCombobox.setBounds(180, 20, 130, 40);
		TabSearchCombobox.addActionListener(this);
		TabbedPanel_Search.add(TabSearchCombobox);

		TabSearchTextArea = new JTextArea();
		TabSearchTextArea.setFont(new Font("굴림", 0, 12)); // 폰트 설정
		TabSearchTextArea.setForeground(Color.black);
		TabSearchTextArea.setOpaque(true); // 투명도
		TabSearchTextArea.setBackground(Color.white);
		TabSearchTextArea.setBorder(null);
		TabSearchTextArea.setEditable(false); // 편집 기능만 비활성화(드래그, 복사 가능)
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(TabSearchTextArea);
		scrollPane.setBounds(20, 80, 450, 180);

		TabbedPanel_Search.add(scrollPane);
		// -------------------------------------------------- //

		// ------------------- 최종 구매 패널 ------------------ //
		TabPaymentTextArea = new JTextArea();
		TabPaymentTextArea.setEditable(false);
		TabPaymentTextArea.setBounds(10, 10, 480, 200);
		TabbedPanel_Payment.add(TabPaymentTextArea);

		TabPaymentBtn_Buy = new JButton("최종 구매");
		TabPaymentBtn_Buy.setBounds(250, 220, 100, 40);
		TabPaymentBtn_Buy.addActionListener(this);
		TabbedPanel_Payment.add(TabPaymentBtn_Buy);

		TabPaymentBtn_Reset = new JButton("리셋");
		TabPaymentBtn_Reset.setBounds(370, 220, 100, 40);
		TabPaymentBtn_Reset.addActionListener(this);
		TabbedPanel_Payment.add(TabPaymentBtn_Reset);
		// -------------------------------------------------- //

		// ------------------- 구매 내역 탭 ------------------- //
		TabNoteTextArea = new JTextArea();
		TabNoteTextArea.setFont(new Font("굴림", 0, 12)); // 폰트 설정
		TabNoteTextArea.setForeground(Color.black);
		TabNoteTextArea.setOpaque(true); // 투명도
		TabNoteTextArea.setBackground(Color.white);
		TabNoteTextArea.setBorder(null);
		TabNoteTextArea.setEditable(false);

		JScrollPane scrollPane_note = new JScrollPane();
		scrollPane_note.setViewportView(TabNoteTextArea);
		scrollPane_note.setBounds(10, 10, 480, 200);
		TabbedPanel_Note.add(scrollPane_note);

		TabNoteLabel_totalTitle = new JLabel();
		TabNoteLabel_totalTitle.setText("KDE 컴퓨터 가게 총 수입: ");
		TabNoteLabel_totalTitle.setBounds(10, 220, 200, 40);
		TabbedPanel_Note.add(TabNoteLabel_totalTitle);

		TabNoteLabel_totalCount = new JLabel();
		TabNoteLabel_totalCount.setBounds(160, 220, 50, 40);
		TabbedPanel_Note.add(TabNoteLabel_totalCount);

		TabNoteBtn_Show = new JButton("조회");
		TabNoteBtn_Show.setBounds(370, 220, 100, 40);
		TabNoteBtn_Show.addActionListener(this);
		TabbedPanel_Note.add(TabNoteBtn_Show);
		// -------------------------------------------------- //
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object buttonAction = e.getSource();
		// 조회 탭 - 콤보 박스
		if (buttonAction == TabSearchCombobox) {
			String table_name = (String) TabSearchCombobox.getSelectedItem();
			DB_Store_Search dcs = new DB_Store_Search();
			dcs.search_table(table_name, TabSearchTextArea);
		}
		// 구매 버튼
		else if (buttonAction == BuyPanelBtn_Buy) {
			pc_model = PCModelInput.getText();
			laptop_model = LaptopModelInput.getText();
			printer_model = PrinterModelInput.getText();

			if (pc_model.equals("") && laptop_model.equals("") && printer_model.equals("")) {
				JOptionPane.showMessageDialog(BuyPanel, "세 개 중에 최소 한 개는 입력해주세요.");
				return ;
			}

			pc_model_count = (Integer) PCCountCombobox.getSelectedItem();
			laptop_model_count = (Integer) LaptopCountCombobox.getSelectedItem();
			printer_model_count = (Integer) PrinterCountCombobox.getSelectedItem();

			PCModelInput.setText("");
			PCCountCombobox.setSelectedIndex(0);
			LaptopModelInput.setText("");
			LaptopCountCombobox.setSelectedIndex(0);
			PrinterModelInput.setText("");
			PrinterCountCombobox.setSelectedIndex(0);

			DB_Store_Buy db_store_buy = new DB_Store_Buy();
			db_store_buy.search_table("PC", pc_model, pc_model_count);
			db_store_buy.search_table("Laptop", laptop_model, laptop_model_count);
			db_store_buy.search_table("Printer", printer_model, printer_model_count);

			if (isCorrectModel) {
				JOptionPane.showMessageDialog(BuyPanel, "정상적으로 추가되었습니다.");
			}
			else {
				pc_model = "";
				laptop_model = "";
				printer_model = "";
				pc_model_count = 0;
				laptop_model_count = 0;
				printer_model_count = 0;
				TabPaymentTextArea.setText("");
				JOptionPane.showMessageDialog(BuyPanel, "입력하신 모델이 존재하지 않습니다.");
			}
			isCorrectModel = true;

		}
		// 닫기 버튼
		else if (buttonAction == BuyPanelBtn_Exit) {
			System.exit(0);
		}
		// 최종구매 버튼
		else if (buttonAction == TabPaymentBtn_Buy) {
			if (TabPaymentTextArea.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "먼저 구매를 해주세요.");
				return ;
			}

			DB_Store_Connect_Transaction dsct = new DB_Store_Connect_Transaction();
			dsct.search_table("PC", pc_model, pc_model_count);
			dsct.search_table("Laptop", laptop_model, laptop_model_count);
			dsct.search_table("Printer", printer_model, printer_model_count);

			JOptionPane.showMessageDialog(null, "최종구매하여 [ 총금액 : $"+total_price+" ] 가 결제되었습니다.");
			total_price = 0;

			Object[] option = {"Yes", "No"};
			int keep_going = JOptionPane.showOptionDialog(null, "계속 구매를 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
			if (keep_going == 0) {
				TabPaymentTextArea.setText("");
			} else {
				System.exit(0);
			}
		}
		// 최종 구매 탭 - 리셋 버튼
		else if (buttonAction == TabPaymentBtn_Reset) {
			pc_model = "";
			laptop_model = "";
			printer_model = "";
			pc_model_count = 0;
			laptop_model_count = 0;
			printer_model_count = 0;
			TabPaymentTextArea.setText("");
			JOptionPane.showMessageDialog(null,"리셋 되었습니다.");
		}
		// 구매 내역 탭 - 조회 버튼
		else if (buttonAction == TabNoteBtn_Show) {
			DB_Store_Search dcs = new DB_Store_Search();
			dcs.search_table("transaction", TabNoteTextArea);
			DB_Store_Connect_Transaction ct = new DB_Store_Connect_Transaction();
			TabNoteLabel_totalCount.setText("$"+ct.calculate_total());
		}
	}
}
