package com.lzc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Main {
	
	public static void randSelect(int[] nums, int n) {
		Random rand = new Random();
		for(int i = 0; i < n; i ++){
			swap(nums , i, rand.nextInt(nums.length-i)+i);
		}
	}
	
	public static void swap(int[] nums, int m , int n){
		int temp = nums[n];
		nums[n] = nums[m];
		nums[m] = temp;
	}

	//入口
	public static void main(String[] args) {
		
		//定义最近出现的随机数组
		//最近出现
		int[] a={2,5,7,10,12,14,17,19,20,22,24,25,27,28,29,33};
		//最近不出现10个以上
		int[] notA = {1,3,4,11,13,21,23,26};
		//4-10个不出现
		int[] notA2 = {6,8,9,15,16,18,30,32};
		//加权出现
		int[] notA3 = {2,5,7,17,24,33};
        
		System.out.println("随机抽取之前：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+" ");
        }
        
        //随机取出3个
        randSelect(a,3);
        //随机取出1个
        randSelect(notA,1);
        randSelect(notA2,1);
        randSelect(notA3,1);
        
        int[] b = {1,2,3,4,5,6};
        for(int i = 0;i < 3; i ++){
        	b[i]= a[i];
		}
        b[3] = notA[0];
        b[4] = notA2[0];
        b[5] = notA3[0];
        
        //简单的选择排序
        for (int i = 0; i < b.length; i++) {
            int min = b[i];
            int n=i; //最小数的索引
            for(int j=i+1;j<b.length;j++){
                if(b[j]<min){  //找出最小的数
                    min = b[j];
                    n = j;
                }
            }
            b[n] = b[i];
            b[i] = min;
            
        }
        //输出随机结果
        System.out.println("\n随机抽取之后：");
        String data = "";
        for(int i = 0;i < b.length; i ++){
        	data += b[i]+" ";
		}
        System.out.print(data+"\n");
        
        //写入文件
//		try {
//			Date date=new Date();
//			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//			File file = new File(df.format(date)+".txt");
//
//			// if file doesnt exists, then create it
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			// true = append file
//			FileWriter fileWritter = new FileWriter(file.getName(), true);
//			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//			bufferWritter.write("\n"+data);
//			bufferWritter.close();
//			
//			System.out.println("Done");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        //读取postgres数据库
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://localhost:5433/ssq";
			Connection con = DriverManager.getConnection(url, "postgres",
					"postgres");
			Statement st = con.createStatement();
			String sql = " select * from history";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				//System.out.print(rs.getInt(1));
				System.out.println(rs.getString(2));
			}
			rs.close();
			st.close();
			con.close();

		} catch (Exception ee) {
			System.out.print(ee.getMessage());
		}
		
		
		
	}
	

}
