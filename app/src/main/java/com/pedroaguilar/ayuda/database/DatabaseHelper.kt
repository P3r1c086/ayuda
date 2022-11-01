package com.pedroaguilar.ayuda.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pedroaguilar.ayuda.entities.Category
import com.pedroaguilar.ayuda.Constants
import com.pedroaguilar.ayuda.LoginFragment
import com.pedroaguilar.ayuda.PerfilFragment
import com.pedroaguilar.ayuda.entities.Article
import com.pedroaguilar.ayuda.entities.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, Constants.DATABASE_NAME,
    null, Constants.DATABASE_VERSION) {
    private lateinit var loginFragment: LoginFragment

    override fun onCreate(db: SQLiteDatabase?) {
        //entity_user es el nombre de la tabla
        val createTableUser = "CREATE TABLE ${Constants.ENTITY_USER} (" +
                "${Constants.PROPERTY_ID_USER} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Constants.PROPERTY_NAME_USER} VARCHAR(30)," +
                "${Constants.PROPERTY_SURNAME} VARCHAR(60), " +
                "${Constants.PROPERTY_EMAIL} VARCHAR(60)," +
                "${Constants.PROPERTY_PASSWORD} VARCHAR(60)," +
                "${Constants.PROPERTY_ADDRESS} VARCHAR(60)," +
                "${Constants.PROPERTY_PHONE} VARCHAR(20))"
        //ejecutamos la sentencia
        db?.execSQL(createTableUser)

        val createTableArticle = "CREATE TABLE ${Constants.ENTITY_ARTICLE} (" +
                "${Constants.PROPERTY_ID_ARTICLE} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Constants.PROPERTY_NAME_ARTICLE} VARCHAR(30)," +
                "${Constants.PROPERTY_CATEGORY_ARTICLE} VARCHAR(60), " +
                "${Constants.PROPERTY_DESCRIPTION_ARTICLE} VARCHAR(260), " +
                "${Constants.PROPERTY_ISFAVORITE_ARTICLE} VARCHAR(60))"
        //ejecutamos la sentencia
        db?.execSQL(createTableArticle)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    /**
     * metodo para leer todos las usuarios de la base de datos
     */
    @SuppressLint("Range")//he tenido que añadir esto para quitar fallos .............................
    fun getAllUsers(): MutableList<User>{
        //instanciamos una lista
        val users: MutableList<User> = mutableListOf()
        //abrimos la lectura de la bd
        val database = this.readableDatabase
        //hacemos la consulta para leer todos los campos de la tabla
        val query = "SELECT * FROM ${Constants.ENTITY_USER}"
        //ejecutamos esa consulta
        val result = database.rawQuery(query, null)
        //evaluamos el resultado. moveToFirst() mueve el cursor a la primera linea de la tabla
        //y nos devolvera un boolean dependiendo de si esta o no vacio. Es decir si puede moverse
        //significa que no esta vacio
        if (result.moveToFirst()){
            do {
                //hacemos una nueva instancia del usuario
                val user = User()
                //a este nuevo objeto user le asignamos las variables
                //en getLong indicamos el numero de columna al que pertenece el id
                user.id = result.getLong(result.getColumnIndex(Constants.PROPERTY_ID_USER))
                user.name = result.getString(result.getColumnIndex(Constants.PROPERTY_NAME_USER))
                user.surname = result.getString(result.getColumnIndex(Constants.PROPERTY_SURNAME))
                user.email = result.getString(result.getColumnIndex(Constants.PROPERTY_EMAIL))
                user.password = result.getString(result.getColumnIndex(Constants.PROPERTY_PASSWORD))
                user.address = result.getString(result.getColumnIndex(Constants.PROPERTY_ADDRESS))
                user.phone = result.getString(result.getColumnIndex(Constants.PROPERTY_PHONE))
                //agregamos este objeto nota a la lista de notas
                users.add(user)
                //este bucle se ejecutara hasta que se mueva a la siguiente columna
            }while (result.moveToNext())
        }
        return users
    }

    /**
     * metodo para leer todos las datos de un usuario
     */
    @SuppressLint("Range")//he tenido que añadir esto para quitar fallos .............................
    fun getUser(): MutableList<String>{
        loginFragment = LoginFragment()
        //instanciamos una lista
        val userData: MutableList<String> = mutableListOf()
        //abrimos la lectura de la bd
        val database = this.readableDatabase
        //hacemos la consulta para leer todos los campos de la tabla
        val query = "SELECT ${Constants.PROPERTY_ID_USER}, " +
                "${Constants.PROPERTY_NAME_USER}, " +
                "${Constants.PROPERTY_SURNAME}, " +
                "${Constants.PROPERTY_EMAIL}, " +
                "${Constants.PROPERTY_ADDRESS}, " +
                "${Constants.PROPERTY_PHONE} " +
                "FROM ${Constants.ENTITY_USER} WHERE ${Constants.PROPERTY_ID_USER} = 3"
        //ejecutamos esa consulta
        val result = database.rawQuery(query, null)
        //evaluamos el resultado. moveToFirst() mueve el cursor a la primera linea de la tabla
        //y nos devolvera un boolean dependiendo de si esta o no vacio. Es decir si puede moverse
        //significa que no esta vacio
        if (result.moveToFirst()){
            do {
                //hacemos una nueva instancia del usuario
//                val user = User()
                //a este nuevo objeto user le asignamos las variables
                //en getLong indicamos el numero de columna al que pertenece el id
                userData.add(0, result.getLong(result.getColumnIndex(Constants.PROPERTY_ID_USER)).toString())
                userData.add(1, result.getString(result.getColumnIndex(Constants.PROPERTY_NAME_USER)).toString())
                userData.add(2, result.getString(result.getColumnIndex(Constants.PROPERTY_SURNAME)).toString())
                userData.add(3, result.getString(result.getColumnIndex(Constants.PROPERTY_EMAIL)).toString())
                userData.add(4, result.getString(result.getColumnIndex(Constants.PROPERTY_ADDRESS)).toString())
                userData.add(5, result.getString(result.getColumnIndex(Constants.PROPERTY_PHONE)).toString())
                //este bucle se ejecutara hasta que se mueva a la siguiente columna
            }while (result.moveToNext())
        }
        return userData
    }

    /**
     * metodo para leer todos las usuarios de la base de datos
     */
    @SuppressLint("Range")//he tenido que añadir esto para quitar fallos .............................
    fun getAllArticles(): MutableList<Article>{
        //instanciamos una lista
        val articles: MutableList<Article> = mutableListOf()
        //abrimos la lectura de la bd
        val database = this.readableDatabase
        //hacemos la consulta para leer todos los campos de la tabla
        val query = "SELECT * FROM ${Constants.ENTITY_ARTICLE}"
        //ejecutamos esa consulta
        val result = database.rawQuery(query, null)
        //evaluamos el resultado. moveToFirst() mueve el cursor a la primera linea de la tabla
        //y nos devolvera un boolean dependiendo de si esta o no vacio. Es decir si puede moverse
        //significa que no esta vacio
        if (result.moveToFirst()){
            do {
                //hacemos una nueva instancia del usuario
                val article = Article()
                //a este nuevo objeto user le asignamos las variables
                //en getLong indicamos el numero de columna al que pertenece el id
                article.id = result.getLong(result.getColumnIndex(Constants.PROPERTY_ID_ARTICLE))
                article.nameArticle = result.getString(result.getColumnIndex(Constants.PROPERTY_NAME_ARTICLE))
                article.categoryArticle = result.getString(result.getColumnIndex(Constants.PROPERTY_CATEGORY_ARTICLE))
                article.description = result.getString(result.getColumnIndex(Constants.PROPERTY_DESCRIPTION_ARTICLE))
                article.isFavorite = result.getInt(result.getColumnIndex(Constants.PROPERTY_ISFAVORITE_ARTICLE)) == Constants.TRUE
                //agregamos este objeto nota a la lista de notas
                articles.add(article)
                //este bucle se ejecutara hasta que se mueva a la siguiente columna
            }while (result.moveToNext())
        }
        return articles
    }

    @SuppressLint("Range")
    fun getArticlesForCategory(category: String): MutableList<Article>{
        //instanciamos una lista
        val articles: MutableList<Article> = mutableListOf()
        //abrimos la lectura de la bd
        val database = this.readableDatabase
        //hacemos la consulta para leer todos los campos de la tabla
        val query = "SELECT * FROM ${Constants.ENTITY_ARTICLE} WHERE ${Constants.PROPERTY_CATEGORY_ARTICLE} = '$category'"
        //ejecutamos esa consulta
        val result = database.rawQuery(query, null)
        //evaluamos el resultado. moveToFirst() mueve el cursor a la primera linea de la tabla
        //y nos devolvera un boolean dependiendo de si esta o no vacio. Es decir si puede moverse
        //significa que no esta vacio
        if (result.moveToFirst()){
            do {
                //hacemos una nueva instancia del usuario
                val article = Article()
                //a este nuevo objeto user le asignamos las variables
                //en getLong indicamos el numero de columna al que pertenece el id
                article.id = result.getLong(result.getColumnIndex(Constants.PROPERTY_ID_ARTICLE))
                article.nameArticle = result.getString(result.getColumnIndex(Constants.PROPERTY_NAME_ARTICLE))
                article.categoryArticle = result.getString(result.getColumnIndex(Constants.PROPERTY_CATEGORY_ARTICLE))
                article.description = result.getString(result.getColumnIndex(Constants.PROPERTY_DESCRIPTION_ARTICLE))
                article.isFavorite = result.getInt(result.getColumnIndex(Constants.PROPERTY_ISFAVORITE_ARTICLE)) == Constants.TRUE
                //agregamos este objeto nota a la lista de notas
                articles.add(article)
                //este bucle se ejecutara hasta que se mueva a la siguiente columna
            }while (result.moveToNext())
        }
        return articles
    }

    @SuppressLint("Range")
    fun getArticlesFavorites() : MutableList<Article>{
        //instanciamos una lista
        val articles: MutableList<Article> = mutableListOf()
        //abrimos la lectura de la bd
        val database = this.readableDatabase
        //hacemos la consulta para leer todos los campos de la tabla
        val query = "SELECT * FROM ${Constants.ENTITY_ARTICLE} WHERE ${Constants.PROPERTY_ISFAVORITE_ARTICLE}=1"
        //ejecutamos esa consulta
        val result = database.rawQuery(query, null)
        //evaluamos el resultado. moveToFirst() mueve el cursor a la primera linea de la tabla
        //y nos devolvera un boolean dependiendo de si esta o no vacio. Es decir si puede moverse
        //significa que no esta vacio
        if (result.moveToFirst()){
            do {
                //hacemos una nueva instancia del usuario
                val article = Article()
                //a este nuevo objeto user le asignamos las variables
                //en getLong indicamos el numero de columna al que pertenece el id
                article.id = result.getLong(result.getColumnIndex(Constants.PROPERTY_ID_ARTICLE))
                article.nameArticle = result.getString(result.getColumnIndex(Constants.PROPERTY_NAME_ARTICLE))
                article.categoryArticle = result.getString(result.getColumnIndex(Constants.PROPERTY_CATEGORY_ARTICLE))
                article.description = result.getString(result.getColumnIndex(Constants.PROPERTY_DESCRIPTION_ARTICLE))
                article.isFavorite = result.getInt(result.getColumnIndex(Constants.PROPERTY_ISFAVORITE_ARTICLE)) == Constants.TRUE
                //agregamos este objeto nota a la lista de notas
                articles.add(article)
                //este bucle se ejecutara hasta que se mueva a la siguiente columna
            }while (result.moveToNext())
        }
        return articles
    }

//    /**
//     * metodo para leer todas las categorias de la base de datos
//     */
//    @SuppressLint("Range")//he tenido que añadir esto para quitar fallos .............................
//    fun getAllCategories(): MutableList<Category>{
//        //instanciamos una lista
//        val categories: MutableList<Category> = mutableListOf()
//        //abrimos la lectura de la bd
//        val database = this.readableDatabase
//        //hacemos la consulta para leer todos los campos de la tabla
//        val query = "SELECT * FROM ${Constants.ENTITY_CATEGORY}"
//        //ejecutamos esa consulta
//        val result = database.rawQuery(query, null)
//        //evaluamos el resultado. moveToFirst() mueve el cursor a la primera linea de la tabla
//        //y nos devolvera un boolean dependiendo de si esta o no vacio. Es decir si puede moverse
//        //significa que no esta vacio
//        if (result.moveToFirst()){
//            do {
//                //hacemos una nueva instancia del usuario
//                val category = Category()
//                //a este nuevo objeto user le asignamos las variables
//                //en getLong indicamos el numero de columna al que pertenece el id
//                category.id = result.getLong(result.getColumnIndex(Constants.PROPERTY_ID_CATEGORY))
//                category.name = result.getString(result.getColumnIndex(Constants.PROPERTY_NAME_CATEGORY))
//
//                //agregamos este objeto nota a la lista de notas
//                categories.add(category)
//                //este bucle se ejecutara hasta que se mueva a la siguiente columna
//            }while (result.moveToNext())
//        }
//        return categories
//    }



    /**
     * este metodo insertara una nota en la base de datos y devolvera el id de la nota introducida,
     * el cual es de tipo Long
     */
    fun insertUser(user: User): Long{

        //abrimos la escritura de la db
        val database = this.writableDatabase
        //instanciamos un abjeto contentValues para poder introducir las propiedades a las que queremos
        //darles algun valor. Como id es autoincremental no se lo debemos proporcionar
        val contentValues = ContentValues().apply {
            //el metono put nos pide clave-valor. La clave sera el nombre de la columna y el valor
            //el que que contenga el campo seleccionado
            put(Constants.PROPERTY_NAME_USER, user.name)
            put(Constants.PROPERTY_SURNAME, user.surname)
            put(Constants.PROPERTY_EMAIL, user.email)
            put(Constants.PROPERTY_PASSWORD, user.password)
            put(Constants.PROPERTY_ADDRESS, user.address)
            put(Constants.PROPERTY_PHONE, user.phone)
        }
        //insertar estos valores en la tabla. Este metodo devuelve un Long, el cual sera asignado como
        //valor de la variable result
        val resultId = database.insert(Constants.ENTITY_USER, null, contentValues)

        return resultId
    }

    /**
     * este metodo insertara una nota en la base de datos y devolvera el id de la nota introducida,
     * el cual es de tipo Long
     */
    fun insertArticle(article: Article): Long{

        //abrimos la escritura de la db
        val database = this.writableDatabase
        //instanciamos un abjeto contentValues para poder introducir las propiedades a las que queremos
        //darles algun valor. Como id es autoincremental no se lo debemos proporcionar
        val contentValues = ContentValues().apply {
            //el metono put nos pide clave-valor. La clave sera el nombre de la columna y el valor
            //el que que contenga el campo seleccionado
            put(Constants.PROPERTY_NAME_ARTICLE, article.nameArticle)
            put(Constants.PROPERTY_CATEGORY_ARTICLE, article.categoryArticle)
            put(Constants.PROPERTY_DESCRIPTION_ARTICLE, article.description)
        }
        //insertar estos valores en la tabla. Este metodo devuelve un Long, el cual sera asignado como
        //valor de la variable result
        val resultId = database.insert(Constants.ENTITY_ARTICLE, null, contentValues)

        return resultId
    }

    /**
     * metodo para actualizar. Devolvera un boolean en caso de que sea o no exitoso
     */
    fun updateArticle(article: Article): Boolean{
        //abrimos la escritura de la db
        val database = this.writableDatabase
        //instanciamos un abjeto contentValues para poder introducir las propiedades a las que queremos
        //darles algun valor. Como id es autoincremental no se lo debemos proporcionar
        val contentValues = ContentValues().apply {
            //el metono put nos pide clave-valor. La clave sera el nombre de la columna y el valor
            //el que que contenga el campo seleccionado
            //put(Constants.PROPERTY_DESCRIPTION, note.description)
            put(Constants.PROPERTY_ISFAVORITE_ARTICLE, article.isFavorite)
        }
        //actualizar este objeto en la bd. La clausula WHERE indica que actualizaremos esta nota donde
        //el id sea igual al num de la nota
        val result = database.update(Constants.ENTITY_ARTICLE, contentValues,
            "${Constants.PROPERTY_ID_ARTICLE} = ${article.id}",
            null)
        //evaluar si fue o no exitoso. Si es exitoso devuelve un 1 sino un 0
        return result == Constants.TRUE
    }
    /**
     * metodo para actualizar. Devolvera un boolean en caso de que sea o no exitoso
     */
    fun updateUser(user: User): Boolean{
        //abrimos la escritura de la db
        val database = this.writableDatabase
        //instanciamos un abjeto contentValues para poder introducir las propiedades a las que queremos
        //darles algun valor. Como id es autoincremental no se lo debemos proporcionar
        val contentValues = ContentValues().apply {
            //el metono put nos pide clave-valor. La clave sera el nombre de la columna y el valor
            //el que que contenga el campo seleccionado
            //put(Constants.PROPERTY_DESCRIPTION, note.description)
            put(Constants.PROPERTY_NAME_USER, user.name)
            put(Constants.PROPERTY_SURNAME, user.surname)
            put(Constants.PROPERTY_EMAIL, user.email)
            put(Constants.PROPERTY_ADDRESS, user.address)
            put(Constants.PROPERTY_PHONE, user.phone)
        }
        //actualizar este objeto en la bd. La clausula WHERE indica que actualizaremos esta nota donde
        //el id sea igual al num de la nota
        val result = database.update(Constants.ENTITY_USER, contentValues,
            "${Constants.PROPERTY_ID_USER} = 3",
            null)
        //evaluar si fue o no exitoso. Si es exitoso devuelve un 1 sino un 0
        return result == Constants.TRUE
    }

    /**
     * metodo para eliminar un articulo. Devolvera un boolean
     */
    fun deleteArticle(article: Article): Boolean{
        //abrimos la escritura de la db
        val database = this.writableDatabase
        //borrar este objeto en la bd. La clausula WHERE indica que borraremos esta nota donde
        //el id sea igual al num id de la nota
        val result = database.delete(Constants.ENTITY_ARTICLE,
            "${Constants.PROPERTY_ID_ARTICLE} = ${article.id}",
            null)
        //evaluar si fue o no exitoso. Si es exitoso devuelve un 1 sino un 0
        return result == Constants.TRUE
    }

    /**
     * metodo para eliminar un usuario. Devolvera un boolean
     */
    fun deleteUser(user: User): Boolean{
        //abrimos la escritura de la db
        val database = this.writableDatabase
        //borrar este objeto en la bd. La clausula WHERE indica que borraremos esta nota donde
        //el id sea igual al num id de la nota
        val result = database.delete(Constants.ENTITY_USER,
            "${Constants.PROPERTY_ID_USER} = ${user.id}",
            null)
        //evaluar si fue o no exitoso. Si es exitoso devuelve un 1 sino un 0
        return result == Constants.TRUE
    }
}