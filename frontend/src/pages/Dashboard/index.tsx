// src/pages/Dashboard/index.tsx

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Dashboard.css'; // Estilos específicos para o Dashboard
import axios from 'axios';

const Dashboard: React.FC = () => {
  // Estados para armazenar os dados de livros, usuários e empréstimos
  const [booksCount, setBooksCount] = useState<number>(0);
  const [usersCount, setUsersCount] = useState<number>(0);
  const [loansCount, setLoansCount] = useState<number>(0);

  useEffect(() => {
    // Função para buscar os dados da API
    const fetchData = async () => {
      try {
        const [booksRes, usersRes, loansRes] = await Promise.all([
          axios.get('/books/count'), // Endpoints da API para contagem de livros
          axios.get('/users/count'), // Endpoints para contagem de usuários
          axios.get('/loans/count'), // Endpoints para contagem de empréstimos
        ]);

        setBooksCount(booksRes.data.count);
        setUsersCount(usersRes.data.count);
        setLoansCount(loansRes.data.count);
      } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="dashboard-container">
      <h1>Dashboard - Library Management</h1>
      <div className="dashboard-overview">
        <div className="dashboard-item">
          <h2>Total de Livros</h2>
          <p>{booksCount}</p>
        </div>
        <div className="dashboard-item">
          <h2>Total de Usuários</h2>
          <p>{usersCount}</p>
        </div>
        <div className="dashboard-item">
          <h2>Total de Empréstimos</h2>
          <p>{loansCount}</p>
        </div>
      </div>
      <div className="dashboard-actions">
        <Link to="/books" className="dashboard-link">
          Gerenciar Livros
        </Link>
        <Link to="/users" className="dashboard-link">
          Gerenciar Usuários
        </Link>
        <Link to="/loans" className="dashboard-link">
          Gerenciar Empréstimos
        </Link>
      </div>
      <div className="dashboard-readme">
      <h1>Bem-vindo ao Sistema de Gestão de Biblioteca</h1>
      <p>
        Este sistema de gerenciamento de biblioteca permite que você adicione, edite e remova livros, gerencie usuários e registre empréstimos de maneira simples e eficiente.
      </p>
      <p>
        Criado como um projeto de portfólio, ele demonstra o uso de tecnologias como Spring Boot no backend e React no frontend.
      </p>

      <h2>Principais Funcionalidades</h2>
      <ul>
        <li><strong>Gerenciamento de Livros:</strong> Adicionar, editar, remover e listar livros.</li>
        <li><strong>Gerenciamento de Usuários:</strong> Registrar, atualizar e remover usuários.</li>
        <li><strong>Empréstimos:</strong> Registrar novos empréstimos e acompanhar devoluções.</li>
      </ul>

      <h2>Instruções de Uso</h2>
      <p>
        Para começar, você pode navegar pelas abas acima para realizar as ações:
      </p>
      <h2>Tecnologias Utilizadas</h2>
      <ul>
        <li><strong>Backend:</strong> Spring Boot, API REST, JPA, Hibernate.</li>
        <li><strong>Frontend:</strong> React, Axios, HTML/CSS.</li>
        <li><strong>Banco de Dados:</strong> MySQL/PostgreSQL.</li>
      </ul>
    </div>
    </div>
  );
};

export default Dashboard;
