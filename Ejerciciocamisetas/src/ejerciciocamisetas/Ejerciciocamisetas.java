package ejerciciocamisetas;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
     
public class Ejerciciocamisetas {
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String csvFile = "./camisetas.csv";
        // Variable para almacenar cada línea del archivo
        String line = "";
        String cvsSplitBy = ";";

        try {
            // Abrir el archivo para lectura
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            // Leer el archivo línea por línea
            int cantidadDeCamisetas = 0;
            while ((line = br.readLine()) != null) {
                    cantidadDeCamisetas++;
            }
            br.close();
            br = new BufferedReader(new FileReader(csvFile));
            
            Almacen a = new Almacen("KOAJ", cantidadDeCamisetas);
            
             for (int i = 0; i < cantidadDeCamisetas; i++){
                line = br.readLine();
                String[] datos = line.split(cvsSplitBy);
                Camisetas c = new Camisetas(datos[0],datos[1],datos[2],datos[3], Integer.parseInt(datos[4]),Integer.parseInt(datos[5]));
                a.cargarInventario(a.camisetasDelAlmacen,i,c);
             }
            
            br.close();
            int p = 1;
            do {
                p = a.imprimirMenu(s);
                a.navegacion(p, s);
            }while (p != 0);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
}
class Almacen {
    String nombre;
    int cantidadDeCamisetas;
    Camisetas[] camisetasDelAlmacen;
    public Almacen(String nombre, int cantidadDeCamisetas){
        this.nombre = nombre;
        this.cantidadDeCamisetas = cantidadDeCamisetas;
        this.camisetasDelAlmacen = new Camisetas[cantidadDeCamisetas];    
    }
    
    public void cargarInventario(Camisetas[] camisetasDelAlmacen,int i, Camisetas c){
        camisetasDelAlmacen[i] = c;
    }
    public void imprimirInventario(){
        int i = 0;
        do{
           this.camisetasDelAlmacen[i].printCamiseta();
           i++;
        }while(i<this.cantidadDeCamisetas);
    }
    public int imprimirMenu(Scanner s){
        int p;
        System.out.println("----------Sistema de almacenamiento----------");
        System.out.println("1] Agrega camisetas a tu inventario");
        System.out.println("2] Consulta las camisetas disponibles de tu inventario");
        System.out.println("3] Consulta tu inventrio total");
        System.out.println("4] Registra la venta de una camiseta");
        System.out.println("5] Calcula el valor total de tu inventario");
        p = s.nextInt();
    return p;
    }
    public void navegacion(int p, Scanner s){
        switch(p){
            case 1:this.addCamisetas(s);
                break;
            case 2:this.consultarCamisetas(s);
                break;            
            case 3:this.imprimirInventario();
                break;        
            
            case 4:this.sellCamiseta(s);
                break;
            
            case 5:this.getInventarioPrice();
                break;
            
        }
    }
    public void getInventarioPrice (){
        int totalDelinventario = 0;
        for (int i = 0; i< this.camisetasDelAlmacen.length; i++){
            totalDelinventario += this.camisetasDelAlmacen[i].precio;
            }

        System.out.println("Valor del inventario: $" +totalDelinventario);
        
    }
    public void consultarCamisetas(Scanner s){
    s.nextLine();
    Camisetas c = this.buscarCamisetas(s);
    if (c != null){
        c.printCamiseta();
    }
    else{
        System.out.print("La camiseta no fue encontrada");
    }
    
    
}
    public void sellCamiseta(Scanner s){
        s.nextLine();
       
        Camisetas c = this.buscarCamisetas(s);
        System.out.print("Ingrese la cantidad de unidades a vender: ");
        int unidadesParaVender = s.nextInt();
        if ((c.cantidad - unidadesParaVender) >= 0){
            System.out.print("CAMISETA: "+ c.codigo+ " VENDIDA CON EXITO");
            c.cantidad -= unidadesParaVender;
            
            
        }
        else if ((c.cantidad - unidadesParaVender) < 0){
            System.out.print("Lo sentimos, la camiseta"+ c.codigo+ " no tiene suficientes unidades disponibles para su venta");
            return;
        }
        ;
    }
    
    
    public Camisetas buscarCamisetas(Scanner s){
        
        System.out.print("Ingrese el codigo de la camiseta: ");
        String codigoDeLaCamiseta = s.nextLine().toUpperCase();
        for (int i = 0; i< this.camisetasDelAlmacen.length; i++){
            if ( this.camisetasDelAlmacen[i].codigo.equals(codigoDeLaCamiseta) ){
                return this.camisetasDelAlmacen[i];
            }
        
        }
        return null;
    }


    public void addCamisetas(Scanner s){
        s.nextLine();
        System.out.println("Ingrese el código:");
        String codigo = s.nextLine();
        System.out.println("Ingrese el nombre:");
        String nombre = s.nextLine();
        System.out.println("Ingrese el equipo:");
        String equipo = s.nextLine();
        System.out.println("Ingrese la talla:");
        String talla = s.nextLine();
        System.out.println("Ingrese el precio:");
        int precio = s.nextInt();
        System.out.println("Ingrese la cantidad:");
        int cantidad = s.nextInt();
        Camisetas c = new Camisetas(codigo,nombre,equipo,talla,precio,cantidad);
        Camisetas []n = new Camisetas[(this.camisetasDelAlmacen.length)+ 1];
        int i = 0;
        do{
           n[i] = this.camisetasDelAlmacen[i];
           i++;
        }while(i< camisetasDelAlmacen.length);
        n[i] = c;
        this.camisetasDelAlmacen = n;
        this.cantidadDeCamisetas++;
        System.out.println("CAMISETA AGREGADA EXITOSAMENTE");
    }
}
class Camisetas{
    String codigo;
    String nombre;
    String equipo;
    String talla;
    int precio;
    int cantidad;
    public Camisetas(String codigo,String nombre,String equipo,String talla,int precio,int cantidad){
        this.codigo = codigo;
        this.nombre = nombre;
        this.equipo = equipo;
        this.talla = talla;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    public void printCamiseta(){
        System.out.printf(
    "%-10s %-25s %-20s %-8s %-10d %-10d%n",
    this.codigo,this.nombre,this.equipo,this.talla,this.precio,this.cantidad);
    
}

}