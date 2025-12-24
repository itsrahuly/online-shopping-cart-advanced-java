<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.UserDAO, java.util.List, model.User" %>

<%@ include file="include/header.jsp"%>
<%@ include file="include/sidebar.jsp"%>

<%
    UserDAO dao = new UserDAO();
    List<User> users = dao.getAllUsers();
%>

<div class="content-area">
    <h3 class="fw-bold mb-3">Manage Users</h3>

    <!-- Desktop Table View -->
    <div class="table-responsive shadow-sm d-none d-md-block">
        <table class="table table-bordered text-center align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <% for(User u : users) { %>
                <tr>
                    <td><%=u.getName()%></td>
                    <td><%=u.getEmail()%></td>
                    <td><%=u.getPhone()%></td>
                    <td><%=u.getRole()%></td>

                    <td>
                        <span class="badge <%= u.getStatus().equals("Active") ? "bg-success" : "bg-secondary" %>">
                            <%=u.getStatus()%>
                        </span>
                    </td>

                    <td class="text-nowrap">
                        <!-- Edit User -->
                        <a href="edit-user.jsp?id=<%=u.getId()%>" 
                           class="btn btn-warning btn-sm me-1">
                            <i class="bi bi-pencil-square"></i>
                        </a>

                        <!-- Toggle Status -->
                        <a href="../ToggleUserStatus?id=<%=u.getId()%>&status=<%= u.getStatus().equals("Active") ? "Blocked" : "Active" %>"
                           class="btn btn-secondary btn-sm me-1">
                            <i class="bi <%=u.getStatus().equals("Active") ? "bi-x-circle" : "bi-check-circle"%>"></i>
                        </a>

                        <!-- Delete -->
                        <a href="../DeleteUser?id=<%=u.getId()%>" 
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('Delete this user?');">
                            <i class="bi bi-trash"></i>
                        </a>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- 📱 Mobile Card View -->
    <div class="d-md-none">
        <% for(User u : users) { %>
        <div class="card shadow-sm mb-3">
            <div class="card-body p-2">
                <b><%=u.getName()%></b>
                <p class="small text-muted m-0"><%=u.getEmail()%></p>
                <p class="small m-0">📞 <%=u.getPhone()%></p>

                <span class="badge <%=u.getStatus().equals("Active")?"bg-success":"bg-secondary"%> mt-1">
                    <%=u.getStatus()%>
                </span>

                <div class="d-flex justify-content-end gap-2 mt-2">
                    <a href="edit-user.jsp?id=<%=u.getId()%>" 
                       class="btn btn-warning btn-sm"><i class="bi bi-pencil-square"></i></a>

                    <a href="../ToggleUserStatus?id=<%=u.getId()%>&status=<%=u.getStatus().equals("Active") ? "Blocked" : "Active" %>"
                       class="btn btn-secondary btn-sm">
                       <i class="bi <%=u.getStatus().equals("Active")?"bi-x-circle":"bi-check-circle"%>"></i>
                    </a>

                    <a href="../DeleteUser?id=<%=u.getId()%>" 
                       class="btn btn-danger btn-sm"
                       onclick="return confirm('Delete this user?');">
                       <i class="bi bi-trash"></i>
                    </a>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>

<style>
    .content-area {
        margin-left: 240px;
        padding: 20px;
        min-height: 100vh;
    }
    @media (max-width:768px) {
        .content-area {
            margin-left: 0;
            padding: 12px;
        }
    }
</style>

<%@ include file="include/footer.jsp"%>
