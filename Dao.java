package com.mwq.dao;

import java.util.Vector;

public class Dao extends BaseDao {

	private static Dao dao;

	static {
		dao = new Dao();
	}

	public static Dao getInstance() {
		return dao;
	}

	// init
	public void initDatabase() {
		String[] tableNames = { "tb_order_item", "tb_order_form", "tb_desk",
				"tb_menu", "tb_sort", "tb_manager", "tb_record" };
		for (int j = 0; j < tableNames.length; j++) {
			delete("delete from " + tableNames[j]);
		}
	}

	// tb_desk
	public Vector sDesk() {
		return selectSomeNote("select * from tb_desk where state='可用'");
	}

	public Vector sDeskNums() {
		return selectSomeValue("select num from tb_desk where state='可用'");
	}

	public Vector sDeskByNum(String num) {
		return selectOnlyNote("select * from tb_desk where num='" + num + "'");
	}

	public boolean iDesk(String num, String seating) {
		String sql = "insert into tb_desk values('" + num + "'," + seating
				+ ",'可用')";
		return insert(sql);
	}

	public boolean uDeskByNum(String num, String seating) {
		return update("update tb_desk set seating=" + seating
				+ ", state='可用' where num='" + num + "'");
	}

	public boolean dDeskByNum(String num) {
		return update("update tb_desk set state='撤消' where num='" + num + "'");
	}

	// tb_manager
	public Vector sManager() {
		return selectSomeNote("select * from tb_manager");
	}

	public Vector sManagerByUsername(String username) {
		return selectOnlyNote("select * from tb_manager where username='"
				+ username + "'");
	}

	public Vector sManagerByRecordNum(String recordNum) {
		return selectOnlyNote("select * from tb_manager where record_num='"
				+ recordNum + "'");
	}

	public void iManager(String[] values) {
		insert("insert into tb_manager(record_num,password,purview_system,purview_sell,purview_personnel,purview_init) values('"
				+ values[0]
				+ "','"
				+ values[1]
				+ "','"
				+ values[2]
				+ "','"
				+ values[3] + "','" + values[4] + "','" + values[5] + "')");
	}

	public boolean uPasswordByRecordNum(String recordNum, String password) {
		return super.update("update tb_manager set password='" + password
				+ "' where record_num='" + recordNum + "'");
	}

	public boolean dManagerByRecordNum(String recordNum) {
		return delete("delete from tb_manager where  record_num='"
				+ recordNum + "'");
	}

	// tb_menu
	public Vector sMenu() {
		return selectSomeNote("select * from tb_menu");
	}

	public Vector sMenuByNum(String num) {
		return selectOnlyNote("select * from tb_menu where num=" + num);
	}

	public Vector sMenuByCode(String code) {
		return selectSomeNote("select * from tb_menu where code like '" + code
				+ "%'");
	}

	public Vector sMenuByName(String name) {
		return selectOnlyNote("select * from tb_menu where name='" + name + "'");
	}

	public String sMenuOfMaxNum() {
		Object object = selectOnlyValue("select max(num) from tb_menu");
		if (object == null) {
			return null;
		} else {
			return object.toString();
		}
	}

	public boolean iMenu(String[] menus) {
		menus[3] = dao.sSortByName(menus[3]).get(0).toString();
		String sql = "insert into tb_menu(num,name,code,sort_id,unit,unit_price,state) values('"
				+ menus[0]
				+ "','"
				+ menus[1]
				+ "','"
				+ menus[2]
				+ "',"
				+ menus[3] + ",'" + menus[4] + "'," + menus[5] + ",'销售')";
		return insert(sql);
	}

	public boolean uMenuByName(String[] menus) {
		menus[3] = dao.sSortByName(menus[3]).get(0).toString();
		String sql = "update tb_menu set num='" + menus[0] + "',code='"
				+ menus[2] + "',sort_id=" + menus[3] + ",unit='" + menus[4]
				+ "',unit_price=" + menus[5] + ",state='销售' where name='"
				+ menus[1] + "'";
		return update(sql);
	}

	public boolean dMenuByName(String name) {
		return update("update tb_menu set state='停售' where name='" + name
				+ "'");
	}

	// tb_order_form
	public String sOrderFormOfMaxId() {
		Object object = selectOnlyValue("select max(num) from tb_order_form");
		if (object == null) {
			return null;
		} else {
			return object.toString();
		}
	}

	public String sOrderFormOfMinDatetime() {
		Object object = selectOnlyValue("select min(consume_date) from tb_order_form");
		if (object == null) {
			return null;
		} else {
			return object.toString();
		}
	}

	public Vector sOrderFormOfDay(String date) {
		return selectSomeNote("select * from tb_order_form where consume_date between '"
				+ date + " 00:00:00' and '" + date + " 23:59:59'");
	}

	public boolean iOrderForm(String[] values) {
		String sql = "insert into tb_order_form(num,desk_num,consume_date, expenditure, record_num) values('"
				+ values[0]
				+ "','"
				+ values[1]
				+ "','"
				+ values[2]
				+ "',"
				+ values[3] + ",'" + values[4] + "')";
		return insert(sql);
	}

	// tb_order_item
	public boolean iOrderItem(String[] values) {
		String sql = "insert into tb_order_item(order_form_num,menu_num,amount, total) values('"
				+ values[0]
				+ "','"
				+ values[1]
				+ "',"
				+ values[2]
				+ ","
				+ values[3] + ")";
		return insert(sql);
	}

	// tb_purview
	public Vector sPurview() {
		return selectSomeValue("select name from tb_purview");
	}

	// tb_record
	public Vector sRecord() {
		return selectSomeNote("select * from tb_record where state='在职'");
	}

	public Vector sRecordOfNumAndName() {
		return selectSomeValue("select num+' '+name from tb_record where num not in(select record_num from tb_manager) and state='在职'");
	}

	public String sRecordOfMaxNum() {
		Object object = selectOnlyValue("select max(num) from tb_record");
		if (object == null) {
			return null;
		} else {
			return object.toString();
		}
	}

	public boolean iRecord(String[] values) {
		String sql = "insert into tb_record(num,name,sex,birthday,id_card,address,state) values('"
				+ values[0]
				+ "','"
				+ values[1]
				+ "','"
				+ values[2]
				+ "',"
				+ values[3] + ",'" + values[4] + "','" + values[5] + "','在职')";
		return insert(sql);
	}

	public boolean dRecordByNum(String num) {
		return update("update tb_record set state='离职' where num='" + num
				+ "'");
	}

	// tb_sort
	public Vector sSort() {
		return selectSomeNote("select name from tb_sort where state='销售'");
	}

	public Vector sSortNames() {
		return selectSomeValue("select name from tb_sort where state='销售'");
	}

	public Vector sSortByName(String name) {
		return selectOnlyNote("select * from tb_sort where name='" + name + "'");
	}

	public boolean iSort(String name) {
		String sql = "insert into tb_sort values('" + name + "','销售')";
		return insert(sql);
	}

	public boolean uSortByName(String name) {
		return update("update tb_sort set state='销售' where name='" + name
				+ "'");
	}

	public boolean dSortByName(String name) {
		return update("update tb_sort set state='停售' where name='" + name
				+ "'");
	}

	// v_order_item_and_menu
	public Vector sOrderItemAndMenuByOrderFormNum(String num) {
		return selectSomeNote("select * from v_order_item_and_menu where order_form_num='"
				+ num + "'");
	}

	// v_menu_and_sort
	public Vector sMenuV() {
		return selectSomeNote("select * from v_menu_and_sort");
	}

	public Vector sMenuVBySortName(String sortName) {
		return selectSomeNote("select * from v_menu_and_sort where sort_name='"
				+ sortName + "'");
	}

	// v_manager
	public Vector sManagerVExceptPassword() {
		return selectSomeNote("select num,name,purview_system,purview_sell,purview_personnel,purview_init from v_manager");
	}

	public Vector sManagerVByName(String name) {
		return selectOnlyNote("select * from v_manager where name='" + name
				+ "'");
	}

	// other

	public String[] monthCheckOut(String num) {
		String values[] = { "——", "——", "——", "——", "——" };
		String sqls[] = {
				"select count(*) from tb_order_form where num like '" + num
						+ "%'",
				"select sum(expenditure) from tb_order_form where num like '"
						+ num + "%'",
				"select avg(expenditure) from tb_order_form where num like '"
						+ num + "%'",
				"select max(expenditure) from tb_order_form where num like '"
						+ num + "%'",
				"select min(expenditure) from tb_order_form where num like '"
						+ num + "%'" };
		for (int i = 0; i < sqls.length; i++) {
			Object value = super.selectOnlyValue(sqls[i]);
			if (value != null) {
				String v = value.toString();
				if (!v.equals("0"))
					values[i] = v;
			}
		}
		return values;
	}

	public Vector yearCheckOut(int year) {
		Vector<Vector> valueV = new Vector<Vector>();
		String sql = "";
		String formatMonth = "";
		String formatDay = "";
		for (int day = 1; day <= 31; day++) {
			Vector rowV = new Vector();// 统计行
			rowV.add(day);// 加入日期
			formatDay = (day < 10 ? "0" + day : "" + day);
			for (int month = 1; month <= 12; month++) {// 做统计
				formatMonth = (month < 10 ? "0" + month : "" + month);
				sql = "select sum(expenditure) from tb_order_form where num like '"
						+ year + formatMonth + formatDay + "%'";
				yearCheckOut(rowV, sql);
			}
			sql = "select sum(expenditure) from tb_order_form where num like '"
					+ year + "__" + formatDay + "%'";// 做列总计
			yearCheckOut(rowV, sql);
			valueV.add(rowV);
		}
		Vector rowV = new Vector();// 总计行
		rowV.add("总计");
		for (int month = 1; month <= 12; month++) {// 做月总计
			formatMonth = (month < 10 ? "0" + month : "" + month);
			sql = "select sum(expenditure) from tb_order_form where num like '"
					+ year + formatMonth + "%'";
			yearCheckOut(rowV, sql);
		}
		sql = "select sum(expenditure) from tb_order_form where num like '"
				+ year + "%'";// 做年总计
		yearCheckOut(rowV, sql);
		valueV.add(rowV);

		return valueV;
	}

	public void yearCheckOut(Vector rowV, String sql) {
		Object value = super.selectOnlyValue(sql);
		if (value == null)
			rowV.add("——");
		else
			rowV.add(value);
	}

}
