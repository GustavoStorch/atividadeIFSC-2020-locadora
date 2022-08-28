package com.gustavo.kayan.igor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gustavo.kayan.igor.entity.Emprestimo;

public class EmprestimoDAO implements DAO<Emprestimo> {

	private ClienteDAO clienteDAO;

	private FilmeDAO filmeDAO;

	public EmprestimoDAO() {
		this.clienteDAO = new ClienteDAO();
		this.filmeDAO = new FilmeDAO();
	}

	@Override
	public Emprestimo get(Long id) {
		Emprestimo emprestimo = null;
		String sql = "select * from locacao where id = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {
				emprestimo = new Emprestimo();

				emprestimo.setId(rset.getLong("id"));
				emprestimo.setDataEmprestimo(rset.getDate("dt_emprestimo"));
				emprestimo.setDataDevolucao(rset.getDate("dt_devolucao"));
				emprestimo.setObservacao(rset.getString("observacao"));
				emprestimo.setCliente(this.clienteDAO.get(rset.getLong("id_cliente")));
				emprestimo.setFilme(this.filmeDAO.get(rset.getLong("id_filme")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprestimo;
	}

	@Override
	public List<Emprestimo> getAll() {
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		String sql = "select * from locacao";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				Emprestimo emprestimo = new Emprestimo();

				emprestimo.setId(rset.getLong("id"));
				emprestimo.setDataEmprestimo(rset.getDate("dt_emprestimo"));
				emprestimo.setDataDevolucao(rset.getDate("dt_devolucao"));
				emprestimo.setObservacao(rset.getString("observacao"));
				emprestimo.setCliente(this.clienteDAO.get(rset.getLong("id_cliente")));
				emprestimo.setFilme(this.filmeDAO.get(rset.getLong("id_filme")));

				emprestimos.add(emprestimo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprestimos;
	}

	@Override
	public int save(Emprestimo emprestimo) {
		String sql = "insert into locacao (dt_emprestimo, dt_devolucao, observacao, id_cliente, id_filme)"
				+ " values (?, ?, ?, ?, ?)";

		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);

			stm.setDate(1, emprestimo.getDataEmprestimo());
			stm.setDate(2, emprestimo.getDataDevolucao());
			stm.setString(3, emprestimo.getObservacao());
			stm.setLong(4, emprestimo.getCliente().getId());
			stm.setLong(5, emprestimo.getFilme().getId());

			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean update(Emprestimo emprestimo, String[] params) {
		String sql = "update locacao set dt_emprestimo = ?, dt_devolucao = ?, observacao = ?, id_cliente = ?, id_filme = ? where id = ?";

		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);

			stm.setDate(1, emprestimo.getDataEmprestimo());
			stm.setDate(2, emprestimo.getDataDevolucao());
			stm.setString(3, emprestimo.getObservacao());
			stm.setLong(4, emprestimo.getCliente().getId());
			stm.setLong(5, emprestimo.getFilme().getId());
			stm.setLong(6, emprestimo.getId());

			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Emprestimo emprestimo) {
		String sql = "delete from locacao where id = ?";

		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);

			stm.setLong(1, emprestimo.getId());
			stm.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
