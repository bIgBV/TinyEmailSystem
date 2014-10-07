package com.email.system;

import javafx.util.converter.TimeStringConverter;
import sun.plugin2.message.Message;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by bIgB on 9/19/2014.
 *
 * This class is the main Model of our application. It handles all the interfacing between the messages
 * database and the application
 */
public class MessageModel implements Serializable{
    // Declaring various properties of the Model.
    String toAddress ;
    String fromAddress;
    String messageSubject;
    String messageContent;
    Timestamp messageTime;
    int messageId;

    public String getToAddress() {
        return toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }

    public int getMessageId() {
        return messageId;
    }

    /**
     * Creates a MessageModel object without the messageId. This is used by the MessageSender servlet
     * as we do not require the message id to be stored when saving the message to the database.
     *
     * @param toAddress address of the user the message is being sent to.
     * @param fromAddress address of the user sending the message.
     * @param messageSubject subject of the message
     * @param messageContent content of the message
     * @param messageTime time when the message was sent
     */
    public MessageModel(String toAddress,String fromAddress, String messageSubject, String messageContent,Timestamp messageTime){
        this.toAddress = toAddress;
        this.messageSubject = messageSubject;
        this.messageContent = messageContent;
        this.messageTime = messageTime;
        this.fromAddress = fromAddress;
    }

    /**
     * Creates a MessageModel object with the messageId property.This is used by all the methods
     * present in this class which query the database for various results as the query returns the messageId
     * which can be used to retrieve a particular message.
     *
     * @param toAddress address of the user the message is being sent to
     * @param fromAddress address of the user sending the message.
     * @param messageSubject subject of the message
     * @param messageContent content of the message
     * @param messageTime time when the message was sent
     * @param messageId the id of the message when stored in the database.
     */
    public MessageModel(String toAddress,String fromAddress, String messageSubject, String messageContent,Timestamp messageTime, int messageId){
        this.toAddress = toAddress;
        this.messageSubject = messageSubject;
        this.messageContent = messageContent;
        this.messageTime = messageTime;
        this.fromAddress = fromAddress;
        this.messageId = messageId;
    }

    /**
     * Inserts a message into the database by getting the various values from the object properties.
     *
     * @param model the object whose values are to be stored in the databse.
     */
    public void sendMessage(MessageModel model) {
        String toAddress = model.toAddress;
        String fromAddress = model.fromAddress;
        String subject = model.messageSubject;
        String content = model.messageContent;
        Timestamp time = model.messageTime;
        int msgDraft = 0; // This is a temporary value until I add the save as draft functionality

        try {
            String sqlDriver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost/emailsystem";
            Class.forName(sqlDriver);

            Connection con = DriverManager.getConnection(url, "root","lpavent");

            String query = "INSERT INTO messages (msg_to, msg_from, msg_subject, msg_matter, msg_date, msg_is_draft) VALUES (?,?,?,?,?,?)";

            // we use a preparedStatement as the values to be inserted can change
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, toAddress);
            stmt.setString(2, fromAddress);
            stmt.setString(3, subject);
            stmt.setString(4, content);
            stmt.setTimestamp(5, time);
            stmt.setInt(6, msgDraft);

            stmt.execute();
            System.out.println("Sending message..");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method returns a list of MessageModel objects which can be iterated over to display
     * the contents of each message. It is declared static so that scriptlets in jsp's can call
     * this method through it's fully qualified named without the need of an object.
     *
     * @param toAddress the user whose messages we need to retrieve.
     * @return a list containing all the messages received by the user.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<MessageModel> getReceivedMessages(String toAddress) throws SQLException, ClassNotFoundException{
        ArrayList<MessageModel> msgList = new ArrayList<MessageModel>();
        String sqlDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/emailsystem";
        Class.forName(sqlDriver);
        Connection con = DriverManager.getConnection(url, "root", "lpavent");

        String query = "SELECT * FROM messages WHERE msg_to='" + toAddress +"' ORDER BY msg_date DESC";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()){
            while(rs.next()){
                String msgTo = rs.getString("msg_to");
                String msgFrom = rs.getString("msg_from");
                String msgSub = rs.getString("msg_subject");
                String msgCnt = rs.getString("msg_matter");
                Timestamp msgTime = rs.getTimestamp("msg_date");
                int msgId = rs.getInt("msg_id");
                MessageModel model = new MessageModel(msgTo,msgFrom,msgSub,msgCnt,msgTime,msgId);
                msgList.add(model);
            }
        }
        return msgList;
    }

    /**
     * Returns a list of messages sent by a user. This is similar to getReceivedMessages() method
     * and is also declared static.
     *
     * @param fromAddress the username whose sent messages we need to retireve.
     * @return the list of all sent messages by a user.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<MessageModel> getSentMessages(String fromAddress) throws SQLException, ClassNotFoundException{
        ArrayList<MessageModel> msgList = new ArrayList<MessageModel>();
        String sqlDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/emailsystem";
        Class.forName(sqlDriver);
        Connection con = DriverManager.getConnection(url, "root", "lpavent");

        String query = "SELECT * FROM messages WHERE msg_from='" + fromAddress +"' ORDER BY msg_date DESC";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()){
            while(rs.next()){
                String msgTo = rs.getString("msg_to");
                String msgFrom = rs.getString("msg_from");
                String msgSub = rs.getString("msg_subject");
                String msgCnt = rs.getString("msg_matter");
                Timestamp msgTime = rs.getTimestamp("msg_date");
                int msgId = rs.getInt("msg_id");

                MessageModel model = new MessageModel(msgTo,msgFrom,msgSub,msgCnt,msgTime,msgId);
                msgList.add(model);
            }
        }
        return msgList;
    }

    /**
     * Retrieves a particular message by the passed id from the database.
     *
     * @param id the id of the message to be retrieved.
     * @return an object of the type MessageModel.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static MessageModel getMessageById(int id) throws SQLException, ClassNotFoundException{

        String msgTo = "";
        String msgFrom = "";
        String msgSub = "";
        String msgCnt = "";
        Timestamp msgTime = null;
        int msgId = 0;

        // Creating a default object which will be sent if some error occurs while retrieving
        // the message from the database
        MessageModel defaultModel = new MessageModel(msgTo, msgFrom, msgSub, msgCnt, msgTime, msgId);

        String sqlDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/emailsystem";
        Class.forName(sqlDriver);
        Connection con = DriverManager.getConnection(url, "root", "lpavent");

        String query = "SELECT * From messages WHERE msg_id=" + id ;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()){
            msgTo = rs.getString("msg_to");
            msgFrom = rs.getString("msg_from");
            msgSub = rs.getString("msg_subject");
            msgCnt = rs.getString("msg_matter");
            msgTime = rs.getTimestamp("msg_date");
            msgId = rs.getInt("msg_id");
            MessageModel model = new MessageModel(msgTo,msgFrom,msgSub,msgCnt,msgTime,msgId);
            return model;
        }
        return defaultModel;
    }

    /**
     * Returns the number of messages received by a particular user.
     *
     * @param userName the username whose messages must be counted.
     * @return the number of messages received by a user.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int numberOfReceivedMessages(String userName) throws SQLException, ClassNotFoundException{
        int numberOfMessages = 0;
        String sqlDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/emailsystem";
        Class.forName(sqlDriver);
        Connection con = DriverManager.getConnection(url, "root", "lpavent");

        String query = "SELECT count(*) From messages WHERE msg_to='" + userName + "'";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()){
            numberOfMessages = rs.getInt(1);
            return numberOfMessages;
        }
        return numberOfMessages;
    }

    /**
     * Returns the number of messages sent by a particular user.
     * @param userName the username whose sent messages must be counted.
     * @return the number of messages sent by the user.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int numberOfSentMessages(String userName) throws SQLException, ClassNotFoundException{
        int numberOfMessages = 0;
        String sqlDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/emailsystem";
        Class.forName(sqlDriver);
        Connection con = DriverManager.getConnection(url, "root", "lpavent");

        String query = "SELECT count(*) From messages WHERE msg_from='" + userName + "'";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()){
            numberOfMessages = rs.getInt(1);
            return numberOfMessages;
        }
        return numberOfMessages;
    }

}