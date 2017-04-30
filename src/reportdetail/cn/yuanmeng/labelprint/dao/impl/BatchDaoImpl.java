package cn.yuanmeng.labelprint.dao.impl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.poi.FileList;
import cn.yuanmeng.labelprint.dao.BaseDao;
import cn.yuanmeng.labelprint.dao.BatchDao;
import cn.yuanmeng.labelprint.entity.Batch;

public class BatchDaoImpl extends BaseDao implements BatchDao {
	public static void main(String a[]){
		BatchDaoImpl bdi=new BatchDaoImpl();
		String sql="select * from batch";
		List<Batch> list=bdi.search(sql);
		int i=1;
		for (Batch batch : list) {
			System.out.print(batch.getSex());
			if(i%100==0){
				System.out.println();
			}
			i++;
		}
	}
	public List<Batch> search(String sql){
		List<Batch> list=new ArrayList<Batch>();
		getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			res=pstmt.executeQuery();
			while(res.next()){
				Batch batch=new Batch();
				batch.setAge(res.getString("age"));
				batch.setBatchNo(res.getString("batchNo"));
				batch.setBranchCompany(res.getString("branchCompany"));
				batch.setCode(res.getString("code"));
				batch.setCollectionDate(res.getString("collectionDate"));
				batch.setContact(res.getString("contact"));
				batch.setEntering(res.getString("entering"));
				batch.setFamilyHistory(res.getString("familyHistory"));
				batch.setGuardian(res.getString("guardian"));
				batch.setGuardianContact(res.getString("guardianContact"));
				batch.setId(res.getInt("id"));
				batch.setIdno(res.getString("idno"));
				batch.setInstitution(res.getString("institution"));
				batch.setName(res.getString("name"));
				batch.setNation(res.getString("nation"));
				batch.setNote(res.getString("note"));
				batch.setPage(res.getString("page"));
				batch.setProject(res.getString("project"));
				batch.setRelation(res.getString("relation"));
				batch.setSalesMan(res.getString("salesMan"));
				batch.setSampleType(res.getString("sampleType"));
				batch.setSex(res.getString("sex"));
				batch.setSamplingDate(res.getString("samplingDate"));
				batch.setSimpleStatus(res.getString("simpleStatus"));
				list.add(batch);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int execute(String sql, String pathFileName) {
		// TODO Auto-generated method stub
		BufferedWriter outSqlException;
		String mes = "";
		int n = 0;
		this.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			n = pstmt.executeUpdate();
		} catch (Exception e) {
			try {
				//BufferedWriter out;
				outSqlException = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("T_2.questionDir" + "\\outSqlException.txt",true)));
				outSqlException.write(pathFileName +"sql语句异常 :\r\n   "+ sql + "\r\n");
				outSqlException.close();
			} catch (IOException e1) {
				e.printStackTrace();
			}
			e.printStackTrace();
		}

		this.closeAll();

		return n;
	}

	@Override
	public List<Batch> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Batch batch) {
		// TODO Auto-generated method stub
		int n = 0;
		String sql = "insert into batch(code,name,sex,age,contact,idno,project,sampleType,branchCompany,salesMan,entering,samplingDate,familyHistory,note,collectionDate,Guardian,relation,guardianContact)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {batch.getCode(),batch.getName(),batch.getSex(),batch.getAge(),batch.getContact(),batch.getIdno(),batch.getProject(),batch.getSampleType(),batch.getBranchCompany(),batch.getSalesMan(),batch.getEntering(),batch.getSamplingDate(),batch.getFamilyHistory(),batch.getNote(),batch.getCollectionDate(),batch.getGuardian(),batch.getRelation(),batch.getGuardianContact()};
		n = this.updateOrInsert(sql, obj);
		return n;
	}

	public int insertByObj(Object[] obj) {
		int n = 0;
		String sql = "insert into batch(code,name,sex,age,contact,idno,project,sampleType,branchCompany,salesMan,entering,samplingDate,familyHistory,note,collectionDate,Guardian,relation,guardianContact)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		n = this.updateOrInsert(sql, obj);
		return n;
	}

	@Override
	public int update(Batch batch) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int del(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int maxId() {
		// TODO Auto-generated method stub
		int max = 0;
		String sql = "select max(id) from batch";
		getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			res = pstmt.executeQuery();
			if (res.next()) {
				max = res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return max + 1;
	}

	// public static void main(String[] args) {
	// BatchDaoImpl b=new BatchDaoImpl();
	// System.out.println(b.maxId());
	// }
}
