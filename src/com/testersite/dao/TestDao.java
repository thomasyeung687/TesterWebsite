/*
 * package com.testersite.dao;
 * 
 * 
 * import java.util.ArrayList; //import
 * org.apache.jasper.tagplugins.jstl.core.Catch; import java.sql.*; import
 * com.testersite.model.Question;
 * 
 * public class TestDao { Connection con = DBConnection.getDBConnection();
 * Statement st; public TestDao() { try { st = con.createStatement(); }catch
 * (Exception e) { System.out.println("Exception occured in constructor!"); } }
 * 
 * public ArrayList<Question> test() { ArrayList<Question> questions = new
 * ArrayList<Question>(); try { ResultSet rSet =
 * st.executeQuery("select*from tester.questionsandans"); rSet.next();// idk if
 * this needs to be here
 * 
 * do{ Question newque = new Question();
 * newque.setQuestionString(rSet.getString("question"));
 * newque.setAns1(rSet.getString("ans1"));
 * newque.setAns2(rSet.getString("ans2"));
 * newque.setAns3(rSet.getString("ans3"));
 * newque.setAns4(rSet.getString("ans4"));
 * newque.setCorrecString(rSet.getString("correctans")); questions.add(newque);
 * System.out.println("Successfully added Question:\n"+newque.toString()); }
 * while(rSet.next()); }catch(SQLException e){
 * System.out.println("Exception occured in ArrayList method!"); } return
 * questions; } }
 */