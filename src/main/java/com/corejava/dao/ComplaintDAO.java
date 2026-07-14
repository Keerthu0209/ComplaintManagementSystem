package com.corejava.dao;
import com.corejava.model.Complaint;
import com.corejava.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {
    public void insertComplaint(Complaint c) {
        String sql = "INSERT INTO complaints (complaint_id, user_id, title, description, category, priority, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getComplaintId());
            ps.setInt(2, c.getUserId());
            ps.setString(3, c.getTitle());
            ps.setString(4, c.getDescription());
            ps.setString(5, c.getCategory());
            ps.setString(6, c.getPriority());
            ps.setString(7, c.getStatus());

            ps.executeUpdate();
            System.out.println("Complaint added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT * FROM complaints";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Complaint c = new Complaint();
                c.setComplaintId(rs.getInt("complaint_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setCategory(rs.getString("category"));
                c.setPriority(rs.getString("priority"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void updateStatus(int complaintId, String status) {
        String sql = "UPDATE complaints SET status = ? WHERE complaint_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, complaintId);
            ps.executeUpdate();
            System.out.println("Status updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
