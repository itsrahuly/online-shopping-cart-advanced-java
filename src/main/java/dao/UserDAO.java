package dao;

import java.sql.*;
import java.util.*;
import model.User;
import util.DBConnection;

public class UserDAO {
	public User login(String email, String password) {
	    User u = null;
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "SELECT * FROM users WHERE email=? AND password=?";
	        PreparedStatement pst = conn.prepareStatement(sql);
	        pst.setString(1, email);
	        pst.setString(2, password);

	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            u = new User();
	            u.setId(rs.getInt("id"));
	            u.setName(rs.getString("name"));
	            u.setEmail(rs.getString("email"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return u;
	}

    // Add User
    public boolean addUser(User user) {
        boolean result = false;

        try (Connection con = DBConnection.getConnection()) {

            // Check if email already exists
            if (isEmailExists(user.getEmail())) {
                return false;
            }

            String sql = "INSERT INTO users(name, email, password, phone, address, role, status) "
                       + "VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getStatus());

            result = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    // List All Users
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users ORDER BY id DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setAddress(rs.getString("address"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    // Get User By ID
    public User getUserById(int id) {
        User u = null;

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setAddress(rs.getString("address"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }


    // Update User
    public boolean updateUser(User u) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET name=?, email=?, phone=?, role=?, status=? WHERE id=?"
            );
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhone());
            ps.setString(4, u.getRole());
            ps.setString(5, u.getStatus());
            ps.setInt(6, u.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Delete User
    public boolean deleteUser(int id) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id=?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Change User Status Active / Blocked
    public boolean updateStatus(int id, String status) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET status=? WHERE id=?");
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Check Email Existing (helps avoid duplicate users)
    public boolean isEmailExists(String email) {
        boolean exists = false;

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id FROM users WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            exists = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
    public int getUserCount() {
        int count = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM users");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) count = rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }
    public boolean registerUser(String name, String email, String password,
            String phone, String address) {

String sql = "INSERT INTO users(name, email, password, phone, address, role, status) "
+ "VALUES(?, ?, ?, ?, ?, 'USER', 'Active')";

try (Connection con = DBConnection.getConnection();
PreparedStatement pst = con.prepareStatement(sql)) {

pst.setString(1, name);
pst.setString(2, email);
pst.setString(3, password);
pst.setString(4, phone);

if (address == null || address.trim().isEmpty())
pst.setNull(5, Types.VARCHAR);
else
pst.setString(5, address);

return pst.executeUpdate() > 0;

} catch (Exception e) {
e.printStackTrace();
}

return false;
}

// Check if email already exists
public boolean isEmailExist(String email) {

String sql = "SELECT id FROM users WHERE email=?";

try (Connection con = DBConnection.getConnection();
PreparedStatement pst = con.prepareStatement(sql)) {

pst.setString(1, email);
ResultSet rs = pst.executeQuery();

return rs.next();

} catch (Exception e) {
e.printStackTrace();
}

return false;
}

}
