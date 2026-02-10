

import java.util.Scanner;
import java.sql.*;

public class ProjectManager {
    public static void main(String[] args) {
        try{
            Connection conn = DriverManager.getConnection(DBConfigPro.url,DBConfigPro.username,DBConfigPro.password);
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.println("----------* Project Management System *------------");
                System.out.println("1. Insert Project");
                System.out.println("2. Update Project Details");
                System.out.println("3. Delete Project");
                System.out.println("4. Search Project");
                System.out.println("5. Display Projects Info");
                System.out.println("6. Exit");
                System.out.print("Enter your Choice : ");
                int choice = sc.nextInt();
                System.out.println();
                switch (choice)
                {
                    case 1:
                        System.out.println("Enter Project Id : ");
                        int id = sc.nextInt();
                        System.out.println("Enter Project Name : ");
                        String name = sc.next();
                        System.out.println("Enter Project Status (true/false) : "); // true indicates complete, false indicates incomplete
                        boolean status = sc.nextBoolean();
                        System.out.println("Enter Start Date (YYYY-MM-DD) : ");
                        String start_date = sc.next();
                        PreparedStatement ps = conn.prepareStatement("INSERT into projects(pid,pname,pstatus,start_date)values(?,?,?,?)");
                        ps.setInt(1,id);
                        ps.setString(2,name);
                        ps.setBoolean(3,status);
                        ps.setString(4,start_date);
                        int res = ps.executeUpdate();
                        if(res>0){
                            System.out.println("Record Inserted Successfully !");
                        }else{
                            System.out.println("Insertion Failed :(");
                        }
                        break;
                    case 2:
                        System.out.print("enter Project ID to update : ");
                        int uid = sc.nextInt();
                        System.out.print("Update the Project Status : ");
                        boolean ustatus = sc.nextBoolean();
                        PreparedStatement ps1 = conn.prepareStatement("UPDATE projects SET pstatus=? where pid=?");
                        ps1.setBoolean(1,ustatus);
                        ps1.setInt(2,uid);
                        int res1 = ps1.executeUpdate();
                        if(res1>0)
                        {
                            System.out.println("Updated Successfully !");
                        }else{
                            System.out.println("Failed to Update :(");
                        }
                        break;
                    case 3:
                        System.out.print("enter Project ID to Delete : ");
                        int did = sc.nextInt();
                        PreparedStatement ps2 = conn.prepareStatement("Delete from projects where pid=?");
                        ps2.setInt(1,did);
                        int res2 = ps2.executeUpdate();
                        if(res2>0)
                        {
                            System.out.println("Deleted Successfully !");
                        }else{
                            System.out.println("Project Not Found :(");
                        }
                        break;
                    case 4:
                        System.out.print("Enter the Project ID to Search : ");
                        int sid = sc.nextInt();
                        PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM projects where pid=?");
                        ps3.setInt(1,sid);
                        ResultSet ans = ps3.executeQuery();
                        if(ans.next())
                        {
                            System.out.println("Project Found : \nID : "+ans.getInt(1)+"\tName : "+ans.getString(2)+"\tStatus : "+ans.getBoolean(3)+"\tStart Date : "+ans.getString(4));
                        }else{
                            System.out.println("Project Not Found !");
                        }
                        break;
                    case 5:
                        PreparedStatement ps4 = conn.prepareStatement("SELECT * FROM projects");
                        ResultSet rs = ps4.executeQuery();
                        System.out.println("-----------* Projects Info *-----------");
                        while(rs.next())
                        {
                            System.out.println("ID : "+rs.getInt(1)+"\t| Name : "+rs.getString(2)+"\t| Status : "+rs.getBoolean(3)+"\t| Start Date : "+rs.getString(4));
                        }
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid Choice, please try again!");
                }
            }
        }catch (SQLException e)
        {
            System.out.println("SQL Error : "+e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
