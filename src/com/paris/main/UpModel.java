/*
	하위 카테고리와 그 카테고리에 등록된 상품의 수 정보를 제공하는 모델
*/
package com.paris.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class UpModel extends AbstractTableModel{
	Vector<String> columnName = new Vector<String>();
	Vector<Vector> data = new Vector<Vector>();		//2차원 배열을 담을 것이기 때문에 제너릭을 Vector형으로 둔다.
	Connection con;
		
	public UpModel(Connection con) {
		this.con=con;
		getList();
	}
	
	//목록 가져오기
	public void getList(){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select s.sub_category_id as sub_category_id, sub_name as 카테고리명, count(product_id) as 갯수");
		sql.append(" from subcategory s left outer join product p");
		sql.append(" on s.sub_category_id = p.subcategory_id");
		sql.append(" group by s.sub_category_id, sub_name");		//자바 안에서는 쿼리문의 마무리인 ;을 적지 않아도 된다.
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			//벡터들을 초기화
			columnName.removeAll(columnName);
			data.removeAll(data);
			
			
			//컬럼명 추출 여기서 컬럼명은 메타데이타이다.
			ResultSetMetaData meta = rs.getMetaData();
			for(int i=1;i<=meta.getColumnCount();i++){		//for문을 1부터 시작해서 0번째에 없는 데이터 제외
				columnName.add(meta.getColumnName(i));
			}
			
			while(rs.next()){
				//레코드 1건을 벡터에 옮겨심자
				//여기서 벡터는 DTO의 역할		JTable이 옛날기술이라 이렇게 활용
				Vector vec = new Vector();
				//알리아스를 이용하여 아래와 같이 활용할 수도 있다.
				vec.add(rs.getString("sub_category_id"));
				vec.add(rs.getString("카테고리명"));
				vec.add(rs.getString("갯수"));
				
				data.add(vec);
				
			}
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
		return columnName.size();
	}

	public String getColumnName(int col) {
		return columnName.get(col);
	}
	
	public int getRowCount() {
		return data.size();		//벡터의 길이
	}

	public Object getValueAt(int row, int col) {
		return data.get(row).get(col);
	}

}
