package br.gui.arvorechar;

public interface IArvoreChar {
	
	public void insert(char valor);
	public void search(char valor);
	public void remove(char valor);
	public void prefixSearch() throws Exception;
	public void infixSearch() throws Exception;
	public void postfixSearch() throws Exception;
	public boolean existis(char valor);
	
}
