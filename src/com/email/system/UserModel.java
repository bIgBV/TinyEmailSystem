package com.email.system;

import java.security.MessageDigest;
import java.sql.*;

class UserModel {
    String passwordHash;
    String username;
    int id;

    UserModel(String passwordHash, String username) {
        this.passwordHash = passwordHash;
        this.username = username;
    }

    /**
     * Generates a SHA-256 hash code of the passed string to store in a database
     * @param password takes the user input
     * @return hex string of the hash
     * @throws Exception
     */
    public static String hashPassword(String password) throws Exception{
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();

            return convertByteArrayToHexString(digest);
        }catch(Exception ex){
            throw new Exception(
                    "Could not generate has from string", ex);
        }
    }

    /**
     * Converts a byte array generated by a message digest algorithm into a hex string
     * @param arrayBytes byte array generated by hashPassword
     * @return the hex string of the array
     */
    static String convertByteArrayToHexString(byte[] arrayBytes){
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < arrayBytes.length; i++){
            // There's some hex arithmetic magic over here, no idea what it is though..
            buffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).toString());
        }
        return buffer.toString();
    }

    /**
     * Registers a user by inserting the username and the password hash into the
     * database
     * @param username
     * @param password
     */
    void registerUser(String username, String password){

        try{
            String pwdHash = hashPassword(password);
            String sqlDriver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost/emailsystem";
            Class.forName(sqlDriver);
            Connection con = DriverManager.getConnection(url, "root", "lpavent");
            System.out.println("Connection success");

            String query = "insert into users (user_name, passwd_hash) values(? , ?)";

            PreparedStatement preparedStmnt = con.prepareStatement(query);
            preparedStmnt.setString(1, username);
            preparedStmnt.setString(2, pwdHash);

            preparedStmnt.execute();
            System.out.println("Inserted into database");

            con.close();

        }
        catch(Exception e){
            e.getMessage();
        }
    }

    /**
     * Returns the hex string of the password hash
     * @param password
     * @return
     */
    String passwordHash(String password) {
        try {
            return hashPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /**
     * Gets the userId of the user in from the database
     * @param username The user entry we want to look up.
     * @return returns a UserModel object.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    static String getUserHash(String username) throws SQLException, ClassNotFoundException {
        String hash = "";
        String sqlDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/emailsystem";
        Class.forName(sqlDriver);
        Connection con = DriverManager.getConnection(url, "root", "lpavent");

        String query = "SELECT * FROM users WHERE user_name='" + username + "'";

        Statement stmnt = con.createStatement();
        ResultSet rs = stmnt.executeQuery(query);
        if (rs.next()) {
            hash = rs.getString("passwd_hash");
            return hash;
        }
        return hash;
    }
}