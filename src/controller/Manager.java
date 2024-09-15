package controller;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.*;

// Manager nó quản lí CRUD 1 cái Product
public class Manager {
    List<Product> listProduct = new ArrayList(); // new : Gửi đơn li dị đến toà án Ram, xin phân chia tài sản 
    // vài MB để nó lưu trữ đồ đạc nhà cửa của nó hậu chia tay
    List<Brand> listBrand = new ArrayList<>();
    List<Category> listCategory = new ArrayList<>(); 
    Scanner sc = new Scanner(System.in);
    // Hàm này mục tiêu là kiểm tra xem id nhập có tồn tại hay ko, nếu tồn tại, thì trả về true
   
    public void loadCateFromFile(String path){
        File newFile = new File(path); // Tạo 1 object file tham chiếu đến đường dẫn
        //  chứa file tại vị trí path
        
        try { // => try => thử làm 1 cái công việc
            Scanner sc = new Scanner(newFile);
            while(sc.hasNextLine() == true ){ // Khi nào hàng kế tiếp != null thì t vẫn còn quét
                String line = sc.nextLine(); // Hoàn tất đọc 1 hàng từ trong file
                String[] listData = line.split(",");
                Category c = new Category(listData[0],listData[1]);
                listCategory.add(c);
            }
        } catch (Exception e) { // catch => nắm lấy, bắt nó , cầm nó
            System.out.println("File not fould!");
        }
    }

    public void loadBrandFromFile(String path){
        File newFile = new File(path); // Tạo 1 object file tham chiếu đến đường dẫn
        //  chứa file tại vị trí path
        
        try { // => try => thử làm 1 cái công việc
            Scanner sc = new Scanner(newFile);
            while(sc.hasNextLine() == true ){ // Khi nào hàng kế tiếp != null thì t vẫn còn quét
                String line = sc.nextLine(); // Hoàn tất đọc 1 hàng từ trong file
                String[] listData = line.split(",");
                Brand b = new Brand(listData[0],listData[1]);
                listBrand.add(b);
            }
        } catch (Exception e) { // catch => nắm lấy, bắt nó , cầm nó
            System.out.println("File not fould!");
        }
    }
    public boolean checkIdFromList(String str, String id){
        switch (str) {
            case "Category":
                for (Category tay : listCategory) {
                    if(tay.getCategoryId().equals(id)){
                        return true;
                    }
                }
                break;
            case "Brand":
                for (Brand tay : listBrand) {
                    if(tay.getBrandId().equals(id)){
                        return true;
                    }
                }
                break;
        }
        return false;
    }
    
    public boolean checkIdExist(String id){
        for (Product tay : listProduct) {
            if(tay.getId().equals(id)){
                return true;
            }
         }
         return false;
    }

    public Product searchProductById(String id){
        for (Product tay : listProduct) {
            if(tay.getId().equals(id)){
                return tay;
            }
         }
         return null;
    }

    public void createProduct(){
        
        //String id = UUID.randomUUID().toString();
        loadBrandFromFile("Brand.txt");
        loadCateFromFile("Category.txt");
        String id,name,cateId,brandId ;
        int modelYear;
        double listPrice;
        // Nhập đến khi nào mà id không tồn thì mới thôi
        
        do{
            System.out.print("Enter ID:");
            id = sc.nextLine();
        }while (checkIdExist(id)==true);
        // Nếu name rỗng => Nhập lại
        
        do{
            System.out.print("Enter name:");
            name=sc.nextLine();
        }while(name.isEmpty());
        
        do{
            System.out.print("Enter CategoryId:");
            cateId = sc.nextLine();
        }while (checkIdFromList("Category", cateId)==false);

        do{
            System.out.print("Enter BrandId:");
            brandId = sc.nextLine();
        }while (checkIdFromList("Brand", brandId)==false);

        do{
            System.out.print("Enter Model Year:");
            modelYear = Integer.parseInt(sc.nextLine()); // Exception => try-catch
        }while (modelYear < 1900 || modelYear > 2100);

        do{
            System.out.print("Enter List Price:");
            listPrice = Double.parseDouble(sc.nextLine()); // Exception => try-catch
        }while (listPrice < 0);
        Product p = new Product(id, name, brandId, cateId, modelYear, listPrice);
        listProduct.add(p);
        System.out.println("Add success!");
        
    }


    public List<Product> searchProduct(String name){
        List<Product> result = new ArrayList(); // Ngôi nhà tình thương
         if(listProduct.size()==0){
            return null;
         }
         for (Product tay : listProduct) {
            if(tay.getName().contains(name)){
                result.add(tay);
            }
         }
         return result;
    }

    public void updateProduct(String id){
        
        Product pSearch = searchProductById(id);
        if(pSearch != null){
            loadBrandFromFile("Brand.txt");
            loadCateFromFile("Category.txt");
            String name,cateId,brandId ;
            int modelYear;
            double listPrice;
            // Nhập đến khi nào mà id không tồn thì mới thôi
            
            do{
                System.out.print("Enter name:");
                name=sc.nextLine();
            }while(name.isEmpty());
            
            do{
                System.out.print("Enter CategoryId:");
                cateId = sc.nextLine();
            }while (checkIdFromList("Category", cateId)==false);

            do{
                System.out.print("Enter BrandId:");
                brandId = sc.nextLine();
            }while (checkIdFromList("Brand", brandId)==false);

            do{
                System.out.print("Enter Model Year:");
                modelYear = Integer.parseInt(sc.nextLine()); // Exception => try-catch
            }while (modelYear < 1900 || modelYear > 2100);

            do{
                System.out.print("Enter List Price:");
                listPrice = Double.parseDouble(sc.nextLine()); // Exception => try-catch
            }while (listPrice < 0);
            pSearch.setName(name);
            pSearch.setBrandId(brandId);
            pSearch.setCategoryId(cateId);
            pSearch.setModelYear(modelYear);
            pSearch.setListPrice(listPrice);
            System.out.println("Update success!");
        } else System.out.println("Product not exist!");
    }

    public void deleteProduct(String id){
        Product p = searchProductById(id);
        if(p != null){
            listProduct.remove(p);
            System.out.println("Delete success!");
        } else System.out.println("Product not exist!");
    }

    public void saveToFile(String path){
        try {
            FileWriter fw = new FileWriter(path); // Tạo 1 object vào cái file mà bạn viết vào
            for (Product p : listProduct) {
                fw.write(p.getId()+", "+ p.getName()+", "+p.getBrandId()+", "+p.getCategoryId()+", "+p.getModelYear()+", "+p.getListPrice());
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void printListProduct(){
        for (Product p : listProduct) {
            System.out.println(p.toString());
        }
    }

// -----------------
    // UpdateInformation => Kiểm tra xem cháu nó có tồn tài hay ko => nếu có thì mới update

}
