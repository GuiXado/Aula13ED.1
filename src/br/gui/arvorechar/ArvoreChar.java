package br.gui.arvorechar;

import java.util.NoSuchElementException;

public class ArvoreChar implements IArvoreChar {

	NoChar raiz;
	
	public ArvoreChar() {
		raiz = null;
	}

	@Override
	public void insert(char valor) {
		NoChar elemento = new NoChar();
		elemento.dado = valor;
		elemento.esquerda = null;
		elemento.direita = null;
		insertLeaf(elemento, raiz);
	}

	private void insertLeaf(NoChar elemento, NoChar raizSubArvore) {
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
	public void search(char valor) {
		try {
			NoChar elemento = nodeSearch(raiz, valor);
			int level = nodeLevel(raiz, valor);
			System.out.println("Dado: " + elemento.dado + " - do nível: " + level);
		} catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
		} catch (NullPointerException e) {
			System.err.println("Valor não encontrado");
		}
	}

	private int nodeLevel(NoChar raizSubArvore, int valor) throws NoSuchElementException {
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

	private NoChar nodeSearch(NoChar raizSubArvore, int valor) throws NoSuchElementException {
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
	public void remove(char valor) {
		try {
			removeChild(raiz, valor);
		} catch (Exception e) {
			System.err.println(e.getMessage());
}
	}

	private void removeChild(NoChar raizSubArvore, char valor) throws Exception {
		if (existis(valor)) { //remoção
			NoChar elemento = nodeSearch(raizSubArvore, valor);
			NoChar parent = nodeParent(null, raiz, valor);
			if (elemento.esquerda != null && elemento.direita != null) { // elemeto tem dois filhos
				NoChar noTroca = elemento.esquerda;
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

	private void removeOneOrZeroLeaf(NoChar parent, NoChar elemento) {
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

	private void change(NoChar parent, NoChar elemento, NoChar novoNo) {
		if (parent.esquerda != null && parent.esquerda.dado == elemento.dado) {
			parent.esquerda = novoNo;
		} else if (parent.direita.dado == elemento.dado) {
			parent.direita = novoNo;
		}
	}

	private NoChar nodeParent(NoChar parent, NoChar raizSubArvore, int valor) throws Exception {
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

	private void prefix(NoChar raizSubArvore) throws Exception {
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

	private void infix(NoChar raizSubArvore) throws Exception {
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

	private void postfix(NoChar raizSubArvore) throws Exception {
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
	public boolean existis(char valor) {
		try {
			nodeSearch(raiz, valor);
			return true;
		} catch (NoSuchElementException | NullPointerException e) {
			return false;
		} 
	}
	
}
