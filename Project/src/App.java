package project.animedb;

import java.sql.ResultSet;
import java.util.*;

public class App 
{
	public static DBHelper helper = new DBHelper();
	
	public static void main_menu() //Main Menu
	{
		System.out.println("1. Login");
		System.out.println("2. Dont have an account? Sign Up");
		System.out.println("3. Exit Application");
		System.out.println("Please enter your choice: ");
	}
	
	public static void user_menu() //User Menu after Login
	{
		System.out.println("What would you like to do?");
		System.out.println("1. Add Anime");
		System.out.println("2. Search Anime");
		System.out.println("3. Delete Anime");
		System.out.println("4. View Anime Database");
		System.out.println("5. Logout");
		System.out.println("Please enter your choice: ");
	}
	
	public static int createAccount(int user_id, String email, String username, String password)
	{
		//Check if duplicate email or username exists
		try
        {
            String sql;
            sql = "select email_id, username from User";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
                //Retrieve by column name
            	String mail = rs.getString("email_id");
                String uname  = rs.getString("username");
                if(email.equals(mail))
                {
            		System.out.println("This email ID already has an account \n");
            		return 0;
                }
                else if(username.equals(uname))
                {
            		System.out.println("Sorry, that username has already been taken \n");
            		return 0;
                }
            }
        }
		catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		//Add User
		try
        {
            String sql;
            sql = "insert into User values ( " + String.valueOf(user_id) + ", '" + email + "', '" + username + "', '" + password + "' )";
            helper.insert(sql);
        }
		catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		System.out.println("Account Created! \n");
        return 1;
	}
	
	public static int login(String username, String password)
	{
		//Login User
		try
        {
            String sql;
            sql = "select username, password from User";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
                //Retrieve by column name
                String uname  = rs.getString("username");
                String pass = rs.getString("password");
                if(username.equals(uname) && password.equals(pass))
                {
            		System.out.println("Logged In \n");
            		return 1;
                }
            }
        }
		catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		System.out.println("Invalid username or password \n");
		return 0;
	}
	
	public static void addAnime(String username, String anime)
	{
		int match = 0, uid = -1, aid = -1;
		
		//Check if anime is in the Database
		try
        {
            String sql;
            sql = "select anime_name from Anime";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
                //Retrieve by column name
                String name  = rs.getString("anime_name");
                if(anime.equals(name))
                {
                	match = 1;
                	break;
                }
            }
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		if(match == 0)
        {
        	System.out.println("Anime does not exist or has not been added to the Database \n");
        	return;
        }
		
		//Check if anime is already in Favourites
		try
        {
            String sql;
            sql = "select t.uid, t.aid from User u, Anime a, UserToAnime t where u.user_id = t.uid and a.anime_id = t.aid and u.username = '" + username + "' and a.anime_name = '" + anime + "'";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
                //Retrieve by column name
            	System.out.println("Anime already present in Favourites \n");
                return;
            }
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		//Retrieve user id
		try
        {
            String sql;
            sql = "select user_id from User where username = '" + username + "'";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
                //Retrieve by column name
                uid  = rs.getInt("user_id");
            }
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		//Retrieve anime id
		try
        {
            String sql;
            sql = "select anime_id from Anime where anime_name = '" + anime + "'";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
                //Retrieve by column name
                aid  = rs.getInt("anime_id");
            }
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		//Add to user-anime association class
		try
        {
            String sql;
            sql = "insert into UserToAnime values ( " + String.valueOf(uid) + ", " + String.valueOf(aid) +  " )";
            helper.insert(sql);
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		System.out.println("Anime added to Favourites \n");
	}
	
	public static void searchAnime(String username, String anime)
	{
		int count = 0;
		
		//Search for anime
		try
        {
            String sql;
            sql = "select a.anime_id, a.anime_name, a.genre_name from User u, Anime a, UserToAnime t where u.user_id = t.uid and a.anime_id = t.aid and u.username = '" + username + "' and a.anime_name = '" + anime + "'";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
            	count += 1;
                //Retrieve by column name
            	int id = rs.getInt("anime_id");
                String name  = rs.getString("anime_name");
                String genre = rs.getString("genre_name");
                System.out.println("ID: " + id + "\nName: " + name + "\nGenre: " + genre + "\n");
                return;
            }
            if(count == 0)
            {
            	System.out.println("Anime not present in Favourites \n");
            	return;
            }
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
	}
	
	public static void deleteAnime(String username, String anime)
	{
		int count = 0, uid = -1, aid = -1;
		
		//Check if anime is present in Favourites
		try
        {
            String sql;
            sql = "select t.uid, t.aid from User u, Anime a, UserToAnime t where u.user_id = t.uid and a.anime_id = t.aid and u.username = '" + username + "' and a.anime_name = '" + anime + "'";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
            	count += 1;
                //Retrieve by column name
            	uid = rs.getInt("uid");
            	aid = rs.getInt("aid");
            }
            if(count == 0)
            {
            	System.out.println("Anime not present in Favourites \n");
            	return;
            }
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		//Delete from Favourites
		try
        {
            String sql;
            sql = "delete from UserToAnime where uid = " + String.valueOf(uid) + " and aid = " + String.valueOf(aid);
            helper.delete(sql);
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		System.out.println("Anime removed from Favourites \n");
	}
	
	public static void viewList()
	{
		//View list of anime in pre-existing database
		try
        {
            String sql;
            sql = "select anime_id, anime_name, genre_name from Anime";
            ResultSet rs = helper.select(sql);
            while(rs.next())
            {
                //Retrieve by column name
            	int id = rs.getInt("anime_id");
                String name  = rs.getString("anime_name");
                String genre = rs.getString("genre_name");
                System.out.println("ID: " + id + "\nName: " + name + "\nGenre: " + genre + "\n");
            }
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
	}
	
    public static void main( String[] args )
    {
    	System.out.println("Welcome to MyAnimeList \n");
    	Scanner in = new Scanner(System.in);
    	
    	helper.initialize();
    	
    	int user_id = 0;
    	
    	while(true)
    	{
    		String email, username, password, anime;
    		int valid;
    		main_menu();
        	int type = Integer.parseInt(in.nextLine());
    		switch(type)
    		{
    		case 1: // Login
    			System.out.println("\nPlease enter your Username:");
    			username = in.nextLine();
    			System.out.println("Please enter your Password:");
    			password = in.nextLine();
    			valid = login(username, password);
    			if(valid == 0)
    				break;
    			int logout_status = 0;
    			while(true)
    			{
    				if(logout_status == 1)
    					break;
    				user_menu();
    				int choice = Integer.parseInt(in.nextLine());
    				switch(choice)
    				{
    				case 1: // Add
    					System.out.println("Enter the name of the anime:");
    					anime = in.nextLine();
    					addAnime(username, anime);
    					break;
    				case 2: // Search
    					System.out.println("Enter the name of the anime:");
    					anime = in.nextLine();
    					searchAnime(username, anime);
    					break;
    				case 3: // Delete
    					System.out.println("Enter the name of the anime:");
    					anime = in.nextLine();
    					deleteAnime(username, anime);
    					break;
    				case 4: // View List
    					viewList();
    					break;
    				case 5: // Logout
    					logout_status = 1;
    					System.out.println("Logged Out \n");
    					break;
    				default:
        				System.out.println("Invalid choice, please try again \n");
        				break;
    				}
    			}
    			break;
    		case 2: // Sign Up
    			System.out.println("\nPlease enter your Email-ID:");
    			email = in.nextLine();
    			System.out.println("Please enter your Username:");
    			username = in.nextLine();
    			System.out.println("Please enter your Password:");
    			password = in.nextLine();
    			user_id += 1;
    			valid = createAccount(user_id, email, username, password);
    			if(valid == 0)
    				user_id -= 1;
    			break;
    		case 3: // Exit App
    			helper.terminate();
    			return;
    		default:
				System.out.println("Invalid choice, please try again \n");
				break;
    		}
    	}
    }
}
