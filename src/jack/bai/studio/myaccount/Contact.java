package jack.bai.studio.myaccount;

public class Contact {
	private String name; // <a href="http://www.it165.net/edu/ewl/"
							// target="_blank" class="keylink">�����</a>������
	private double value; // <a href="http://www.it165.net/edu/ewl/"
							// target="_blank" class="keylink">�����</a>��Ӧ����ռ�г��ݶ�ֵ
	private String color; // ������ͼ������ʾ����ɫ

	/**
	 * ���캯��
	 * 
	 * @param name
	 *            �����������
	 * @param value
	 *            �������Ӧ����ռ�г��ݶ�ֵ
	 * @param color
	 *            ������ͼ������ʾ����ɫ
	 */
	public Contact(String name, double value, String color) {
		this.name = name;
		this.value = value;
		this.color = color;
	}

	// ����������ʵ��������getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
