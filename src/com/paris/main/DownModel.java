/*
	하위카테고리에 등록된 상품 정보 제공 모델
*/
package com.paris.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class DownModel extends AbstractTableModel {
	Vector<String> columnName = new Vector<String>();
	Vector<Vector>data = new Vector<Vector>();
	Connection con;
	
	public DownModel(Connection con) {
		this.con=con;
		columnName.add("product_id");
		columnName.add("subcategory_id");
		columnName.add("product_name");
		columnName.add("price");
		columnName.add("img");
		
		
	}
	
	//마우스로 유저가 클릭할 때마다 id값이 바뀌므로 아래의 메서드를 그 때마다 호출하자
	public void getList(int subcategory_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from product";
		sql+=" where subcategory_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, subcategory_id);		//사실 여기서 setString으로 선언해도 상관없다. sql문이 날아갈때 String형으로 날아가서... 공부목적으로 자료형을 명시할 뿐
			rs = pstmt.executeQuery();
			
			//벡터들 초기화	
			data.removeAll(data);
				
			while(rs.next()){
				Vector vec = new Vector();
				vec.add(rs.getInt("product_id"));	//boxing
				vec.add(rs.getString("subcategory_id"));
				vec.add(rs.getString("product_name"));
				vec.add(rs.getString("price"));
				vec.add(rs.getString("img"));
				
				data.add(vec);
			}	
			//System.out.println("data 사이즈는 "+data.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public int getColumnCount() {
		//System.out.println("컬럼의 갯수는 "+columnName.size());
		return columnName.size();
	}
	
	public String getColumnName(int col) {	
		return columnName.get(col);
	}
	public int getRowCount() {
		//System.out.println("레코드의 갯수는 "+data.size());
		return data.size();
	}

	public Object getValueAt(int row, int col) {
		//return data.get(row).get(col);
		Object value = data.get(row).get(col);
		//System.out.println("getValue 호출 "+value);		//객체를 출력하면 객체의 주소값이 나온다.
		return value;
	}

	
}
