package posjavamavenhibernate;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {

	@Test
	public void testeHibernateUtil() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setNome("Jose");
		pessoa.setSobrenome("Pereira");
		pessoa.setEmail("jose@javaavancado.com.br");
		pessoa.setLogin("Jose");
		pessoa.setSenha("Jose123");
		pessoa.setIdade(20);

		daoGeneric.salvar(pessoa);

	}

	@Test
	public void testeBuscar() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(2L);

		pessoa = daoGeneric.pesquisar(pessoa);

		System.out.println(pessoa);

	}

	@Test
	public void testeBuscar2() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(1L, UsuarioPessoa.class);

		System.out.println(pessoa);

	}

	@Test
	public void testeUpadate() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(1L, UsuarioPessoa.class);

		pessoa.setIdade(99);
		pessoa.setNome("Nome atualizado Hibernate");
		pessoa.setSenha("1234");

		pessoa = daoGeneric.updateMerge(pessoa);
		System.out.println(pessoa);

	}

	@Test
	public void testeDelete() throws Exception {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(3L, UsuarioPessoa.class);

		daoGeneric.deletarPorId(pessoa);

	}

	@Test
	public void testeConsultar() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.listar(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : list) {

			System.out.println(usuarioPessoa);
			System.out.println("----------------------------");

		}

	}

	@Test
	public void testeQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa").getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {

			System.out.println(usuarioPessoa);

		}
	}

	@Test
	public void testeQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa order by id")
				.setMaxResults(4).getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {

			System.out.println(usuarioPessoa);

		}
	}

	@Test
	public void testeQueryListParameter() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager()
				.createQuery(" from UsuarioPessoa where nome = :nome or sobrenome = :sobrenome")
				.setParameter("nome", "Fernando").setParameter("sobrenome", "Pereira").getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {

			System.out.println(usuarioPessoa);

		}
	}

	@Test
	public void testeQuerySomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		Double somaIdade = (Double) daoGeneric.getEntityManager()
				.createQuery("select avg(u.idade) from UsuarioPessoa u ").getSingleResult();

		System.out.println("A soma de todas as idade é --> " + somaIdade);

	}

	@Test
	public void testeNameQuery1() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.todos")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);

		}
	}

	@Test
	public void testeNameQuery2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().
				createNamedQuery("UsuarioPessoa.buscaPorNome")
				.setParameter("nome", "Fernando")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);

		}
	}
	
	@Test
	public void testeGravaTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();	
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(7L, UsuarioPessoa.class);
		TelefoneUser telefoneUser = new TelefoneUser();
		telefoneUser.setNumero("63 98444 -5432");
		telefoneUser.setTipo("Celular");
		telefoneUser.setUsuarioPessoa(pessoa);
		daoGeneric.salvar(telefoneUser);
		
	}
	
	@Test
	public void ConsultaTelefones() {
		DaoGeneric daoGeneric = new DaoGeneric();	
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(7L, UsuarioPessoa.class);
		
		for (TelefoneUser fone : pessoa.getTelefoneUsers()) {
			System.out.println(fone.getNumero());
			System.out.println(fone.getTipo());
			System.out.println(fone.getUsuarioPessoa().getNome());
			System.out.println("-------------------------------------------");

		}
		
	}
}
