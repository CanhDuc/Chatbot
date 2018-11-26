import controller.ClassificationController;

public class TestOpenNLP {

	public static void main(String[] args) {
		System.out.println(ClassificationController.getInstance().getResponseFrom("sudo make me sandwich"));
	}

}
