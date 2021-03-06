package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

	//Estas são credenciais padrões, deve-se mudá-las para as credenciais reais do seu banco
	public static String ENDERECO_BANCO = "jdbc:mysql://127.0.0.1:3306/db_name";
	public static String USERNAME = "root";
	public static String SENHA = "";

	/* O método Main() é utilizado para testar a conexão, sem que seja
	 Necessãrio Rodar a aplicação inteira. */
	public static void main(String[] args) {
		String statusDaConexao = null;
		ConexaoMySQL.EstabelecerConexao(statusDaConexao);
		System.out.println(statusDaConexao);

	}

	/* Este é o método usado pela aplicação para obter uma instância do
	 banco de dados MySQL, podendo assim fazer operações DML na base
	 especificada.
	 Este método tem como parâmetro uma String que notifica o status da
	 conexão
	 caso seja preciso.
	
	@return Connection - conexão com o banco Mysql, usada por todos os módulos
	@param statusDaConexao - variável alterada para o atual estado da conexao com o BD

	  */
	public static Connection EstabelecerConexao(String statusDaConexao) {

		Connection conexao = null;

		try {

			String driverName = "com.mysql.jdbc.Driver"; // JDBC Driver padrão
			Class.forName(driverName);

			// Esta operação pode levar até 6 segundos
			conexao = DriverManager.getConnection(ENDERECO_BANCO, USERNAME, SENHA);

			if (testarConexao(conexao))
				statusDaConexao = "Conectado com Sucesso!";
			else
				statusDaConexao = "Erro de Autenticação de credenciais no banco de dados";

			return conexao;

		}
		/* A execao ocorre quando o destido não é alcançado,
		 ou seja, quando a URL é nula ou quando o endereço é inatingível
		 (provavelmente porque não há conexão com a rede) */
		catch (Exception e) {

			statusDaConexao = "Erro de comunicação com o banco de dados";

			return null;

		}

	}

	private static boolean testarConexao(Connection conexao) {

		if (conexao != null)
			return true;
		else
			return false;

	}

	public static boolean FecharConexao() {

		try {
			ConexaoMySQL.EstabelecerConexao(null).close();
			return true;

		} catch (SQLException e) {
			return false;
		}

	}

	public static Connection ReiniciarConexao() {
		FecharConexao();
		return ConexaoMySQL.EstabelecerConexao(null);
	}

}
