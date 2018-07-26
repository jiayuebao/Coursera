public class QuickUnionUF{
	private int[] id;
	private int[] sz;

	public QuickUnionUF(int N){
		id = new int[N];
		sz = new int[N];
		for(int i=0;i<N;i++)
			id[i] = i;
	}

	private int root(int i){
		while (i!=id[i]) {
			id[i]=id[id[i]];//flat the tree
			i = id[i];
		}
		return i;
	}

	public boolean connected(int p, int q){
		return root(p) == root(q);
	}

	public void union(int p, int q){
		int rootp = root(p);
		int rootq = root(q);
		if(rootp == rootq) return;
		if(sz[rootp] < sz[rootq]){// small tree is put under the big tree
			id[rootp] = rootq;
			sz[rootq] += sz[rootp]; 
		}
		else{
			id[rootq] = rootp;
			sz[rootp] += sz[rootq];
		}
		
	}
}