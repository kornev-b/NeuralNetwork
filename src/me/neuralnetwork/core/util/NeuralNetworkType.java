package me.neuralnetwork.core.util;

public enum NeuralNetworkType {
	ADALINE("Adaline"),
	PERCEPTRON("Perceptron"),
	MULTI_LAYER_PERCEPTRON("Multi Layer Perceptron"),
	HOPFIELD("Hopfield"),
	KOHONEN("Kohonen"),
	NEURO_FUZZY_REASONER("Neuro Fuzzy Reasoner"),
	SUPERVISED_HEBBIAN_NET("Supervised Hebbian network"),
	UNSUPERVISED_HEBBIAN_NET("Unsupervised Hebbian network"),
	COMPETITIVE("Competitive"),
	MAXNET("Maxnet"),
	INSTAR("Instar"),
	OUTSTAR("Outstar"),
	RBF_NETWORK("RBF Network"),
	BAM("BAM"),
        BOLTZMAN("Boltzman"),
        COUNTERPROPAGATION("CounterPropagation"),
        INSTAR_OUTSTAR("InstarOutstar"),
        PCA_NETWORK("PCANetwork"),
	RECOMMENDER("Recommender");

	private String typeLabel;
	
	private NeuralNetworkType(String typeLabel) {
		this.typeLabel = typeLabel;
	}
	
	public String getTypeLabel() {
		return typeLabel;
	}
}
