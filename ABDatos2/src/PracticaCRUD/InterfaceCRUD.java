package PracticaCRUD;

//Interfaz para relacionar la base de datos con las acciones del usuario
public interface InterfaceCRUD {
	void menu();
	void insertar();
    void listar();
    void buscar();    
    void modificar();
    void eliminar();
    void cerrar();

}
