<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="include/header.jsp" %> 
<%@ page import="dao.ProductDAO, model.Product, java.util.*" %> 
<% ProductDAO pdao = new ProductDAO(); List<Product> productList = pdao.getAllProducts(); 
%> 
<div class="container"> 
<div class="bg-white p-4 rounded shadow-sm mb-4 text-center">
 <h3 class="fw-bold">Welcome to Online Shopping 🛍️</h3>
  <p class="text-muted">Best Deals • Fast Delivery • Trusted Shopping</p>
   </div> <h4 class="fw-bold mb-3">Latest Products</h4> <div class="row g-4">
    <% for(Product p : productList) 
    { %> <div class="col-md-3 col-sm-6 col-6"> 
    <div class="card shadow-sm h-100">
     <a href="product-detail.jsp?id=<%=p.getId()%>">
      <img src="${pageContext.request.contextPath}/product-images/<%=p.getImage1()%>" 
      class="card-img-top p-2" style="height:200px; object-fit:contain;"> 
      </a> <div class="card-body text-center">
       <h6 class="fw-semibold text-truncate">
       
       <%=p.getName()%>
       </h6> <p class="fw-bold text-danger fs-5">₹
        <%=p.getPrice()%></p> 
       <a href="product-detail.jsp?id=<%=p.getId()%>"
        class="btn btn-primary w-100 btn-sm mb-2">
        Buy Now </a>
        <form action="${pageContext.request.contextPath}/add-to-cart" method="post">
         <input type="hidden" name="id" value="<%=p.getId()%>"> 
         <input type="hidden" name="qty" value="1"> 
         <button class="btn btn-success w-100 btn-sm"> 
         <i class="bi bi-cart-plus"></i>
          Add to Cart </button>
           </form> </div> </div> </div> 
          <% } %> </div> </div> <%@ include file="include/footer.jsp" %>