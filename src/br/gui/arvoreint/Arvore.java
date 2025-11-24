package br.gui.arvoreint;

import java.util.NoSuchElementException;

public class Arvore implements IArvore {

	No raiz;
	
	public Arvore() {
		raiz = null;
	}

	@Override
	public void insert(int valor) {
		No elemento = new No();
		elemento.dado = valor;
		elemento.esquerda = null;
		elemento.direita = null;
		insertLeaf(elemento, raiz);
	}

	private void insertLeaf(No elemento, No raizSubArvore) {
		if (raiz == null) {
			raiz = elemento;
		} else {
			if (elemento.dado < raizSubArvore.dado) {
				if (raizSubArvore.esquerda == null) {
					raizSubArvore.esquerda = elemento;
				} else {
					insertLeaf(elemento, raizSubArvore.esquerda);
				}
			}
			if (elemento.dado > raizSubArvore.dado) {
				if (raizSubArvore.direita == null) {
					raizSubArvore.direita = elemento;
				} else {
					insertLeaf(elemento, raizSubArvore.direita);
				}
			}
		}
	}

	@Override
	public void search(int valor) {
		try {
			No elemento = nodeSearch(raiz, valor);
			int level = nodeLevel(raiz, valor);
			System.out.println("Dado: " + elemento.dado + " - do nível: " + level);
		} catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
		} catch (NullPointerException e) {
			System.err.println("Valor não encontrado");
		}
	}

	private int nodeLevel(No raizSubArvore, int valor) throws NoSuchElementException {
		if (raiz == null) {
			throw new NoSuchElementException("Arvore vazia");
		} else if (valor < raizSubArvore.dado) {
			return 1 + nodeLevel(raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return 1 + nodeLevel(raizSubArvore.direita, valor);
		} else {
			return 0;
		}
	}

	private No nodeSearch(No raizSubArvore, int valor) throws NoSuchElementException {
		if (raiz == null) {
			throw new NoSuchElementException("Arvore vazia");
		} else if (valor < raizSubArvore.dado) {
			return nodeSearch(raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return nodeSearch(raizSubArvore.direita, valor);
		} else {
			return raizSubArvore;
		}
	}

	@Override
	public void remove(int valor) {
		try {
			removeChild(raiz, valor);
		} catch (Exception e) {
			System.err.println(e.getMessage());
}
	}

	private void removeChild(No raizSubArvore, int valor) throws Exception {
		if (existis(valor)) { //remoção
			No elemento = nodeSearch(raizSubArvore, valor);
			No parent = nodeParent(null, raiz, valor);
			if (elemento.esquerda != null && elemento.direita != null) { // elemeto tem dois filhos
				No noTroca = elemento.esquerda;
				while (noTroca.direita != null){
					noTroca = noTroca.direita;
				}
				parent = nodeParent(null, raiz, noTroca.dado);
				elemento.dado = noTroca.dado;
				noTroca.dado = valor;
				removeOneOrZeroLeaf(parent, noTroca);
			} else { // elemento tem 0 ou 1 filho
				removeOneOrZeroLeaf(parent, elemento);
			}
		} else {
			throw new Exception("Valor inexistente");
		}
	}

	private void removeOneOrZeroLeaf(No parent, No elemento) {
		if (elemento.esquerda == null && elemento.direita == null) { 
			// Elemento tem zero filhos
			if (parent == null) {
				raiz = null;
			} else {
				change(parent, elemento, null);
			}
			
		} else if (elemento.direita != null) { 
			// Elemento tem um filho a direita
			if (parent == null) { 
				raiz = elemento.direita;
			} else { 
				// Elemento tem um filho a direita
				change(parent, elemento, elemento.direita);
			}
			
		} else {
			if (parent == null) { 
				// Elemento tem um filho à esquerda
				raiz = elemento.esquerda;
			} else { 
				// Elemento tem um filho a esquerda
				change(parent, elemento, elemento.esquerda);
			}
		}
	}

	private void change(No parent, No elemento, No novoNo) {
		if (parent.esquerda != null && parent.esquerda.dado == elemento.dado) {
			parent.esquerda = novoNo;
		} else if (parent.direita.dado == elemento.dado) {
			parent.direita = novoNo;
		}
	}

	private No nodeParent(No parent, No raizSubArvore, int valor) throws Exception {
		if (raiz == null) {
			throw new Exception("Arvore vazia");
		} else if (valor < raizSubArvore.dado) {
			return nodeParent(raizSubArvore, raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return nodeParent(raizSubArvore, raizSubArvore.direita, valor);
		} else {
			return parent;
		}
	}

	@Override
	public void prefixSearch() throws Exception {
		prefix(raiz);
	}

	private void prefix(No raizSubArvore) throws Exception {
		if (raiz == null) {
			throw new Exception("Arvore vazia");
		} else {
			System.out.print(raizSubArvore.dado + " ");
			if (raizSubArvore.esquerda != null) {
				prefix(raizSubArvore.esquerda);
			} 
			if (raizSubArvore.direita != null) {
				prefix(raizSubArvore.direita);
			} 
		}
	}

	@Override
	public void infixSearch() throws Exception {
		infix(raiz);		
	}

	private void infix(No raizSubArvore) throws Exception {
		if (raiz == null) {
			throw new Exception("Arvore vazia");
		} else {
			if (raizSubArvore.esquerda != null) {
				infix(raizSubArvore.esquerda);
			} 
			System.out.print(raizSubArvore.dado + " ");
			if (raizSubArvore.direita != null) {
				infix(raizSubArvore.direita);
			} 
		}
	}

	@Override
	public void postfixSearch() throws Exception {
		postfix(raiz);
	}

	private void postfix(No raizSubArvore) throws Exception {
		if (raiz == null) {
			throw new Exception("Arvore vazia");
		} else {
			if (raizSubArvore.esquerda != null) {
				postfix(raizSubArvore.esquerda);
			} 
			if (raizSubArvore.direita != null) {
				postfix(raizSubArvore.direita);
			} 
			System.out.print(raizSubArvore.dado + " ");
		}
	}

	@Override
	public boolean existis(int valor) {
		try {
			nodeSearch(raiz, valor);
			return true;
		} catch (NoSuchElementException | NullPointerException e) {
			return false;
		} 
	}
	
}
