import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AddressBookPane pane = new AddressBookPane();
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("styles.css");
		primaryStage.setTitle("AddressBook");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);
	}
}

class AddressBookPane extends GridPane {
	private RandomAccessFile raf;
	// Text fields
	private TextField jtfName = new TextField();
	private TextField jtfStreet = new TextField();
	private TextField jtfCity = new TextField();
	private TextField jtfState = new TextField();
	private TextField jtfZip = new TextField();
	// Buttons
	private AddButton jbtAdd;
	private FirstButton jbtFirst;
	private NextButton jbtNext;
	private PreviousButton jbtPrevious;
	private LastButton jbtLast;
	private Sort1Button jbtSort1;
	private Sort2Button jbtSort2;
	private IterButton jbtIter;
	public EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};

	public AddressBookPane() { // Open or create a random access file
		try {
			raf = new RandomAccessFile("address.dat", "rw");
		} catch (IOException ex) {
			System.out.print("Error: " + ex);
			System.exit(0);
		}
		jtfState.setAlignment(Pos.CENTER_LEFT);
		jtfState.setPrefWidth(25);
		jtfZip.setPrefWidth(60);
		jbtAdd = new AddButton(this, raf);
		jbtFirst = new FirstButton(this, raf);
		jbtNext = new NextButton(this, raf);
		jbtPrevious = new PreviousButton(this, raf);
		jbtLast = new LastButton(this, raf);
		jbtSort1 = new Sort1Button(this, raf);
		jbtSort2 = new Sort2Button(this, raf);
		jbtIter = new IterButton(this, raf);
		Label state = new Label("State");
		Label zp = new Label("Zip");
		Label name = new Label("Name");
		Label street = new Label("Street");
		Label city = new Label("City");
		// Panel p1 for holding labels Name, Street, and City
		GridPane p1 = new GridPane();
		p1.add(name, 0, 0);
		p1.add(street, 0, 1);
		p1.add(city, 0, 2);
		p1.setAlignment(Pos.CENTER_LEFT);
		p1.setVgap(8);
		p1.setPadding(new Insets(0, 2, 0, 2));
		GridPane.setVgrow(name, Priority.ALWAYS);
		GridPane.setVgrow(street, Priority.ALWAYS);
		GridPane.setVgrow(city, Priority.ALWAYS);
		// City Row
		GridPane adP = new GridPane();
		adP.add(jtfCity, 0, 0);
		adP.add(state, 1, 0);
		adP.add(jtfState, 2, 0);
		adP.add(zp, 3, 0);
		adP.add(jtfZip, 4, 0);
		adP.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHgrow(jtfCity, Priority.ALWAYS);
		GridPane.setHgrow(jtfState, Priority.ALWAYS);
		GridPane.setHgrow(jtfZip, Priority.ALWAYS);
		GridPane.setVgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfState, Priority.ALWAYS);
		GridPane.setVgrow(jtfZip, Priority.ALWAYS);
		GridPane.setVgrow(state, Priority.ALWAYS);
		GridPane.setVgrow(zp, Priority.ALWAYS);
		// Panel p4 for holding jtfName, jtfStreet, and p3
		GridPane p4 = new GridPane();
		p4.add(jtfName, 0, 0);
		p4.add(jtfStreet, 0, 1);
		p4.add(adP, 0, 2);
		p4.setVgap(1);
		GridPane.setHgrow(jtfName, Priority.ALWAYS);
		GridPane.setHgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setHgrow(adP, Priority.ALWAYS);
		GridPane.setVgrow(jtfName, Priority.ALWAYS);
		GridPane.setVgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setVgrow(adP, Priority.ALWAYS);
		// Place p1 and p4 into jpAddress
		GridPane jpAddress = new GridPane();
		jpAddress.add(p1, 0, 0);
		jpAddress.add(p4, 1, 0);
		GridPane.setHgrow(p1, Priority.NEVER);
		GridPane.setHgrow(p4, Priority.ALWAYS);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		GridPane.setVgrow(p4, Priority.ALWAYS);
		// Set the panel with line border
		jpAddress.setStyle("-fx-border-color: grey;" + " -fx-border-width: 1;" + " -fx-border-style: solid outside ;");
		// Add buttons to a panel
		FlowPane jpButton = new FlowPane();
		jpButton.setHgap(1);
		jpButton.getChildren().addAll(jbtAdd, jbtFirst, jbtNext, jbtPrevious, jbtLast, jbtSort1, jbtSort2, jbtIter);
		jpButton.setAlignment(Pos.CENTER);
		GridPane.setVgrow(jpButton, Priority.NEVER);
		GridPane.setVgrow(jpAddress, Priority.ALWAYS);
		GridPane.setHgrow(jpButton, Priority.ALWAYS);
		GridPane.setHgrow(jpAddress, Priority.ALWAYS);
		// Add jpAddress and jpButton to the stage
		this.setVgap(5);
		this.add(jpAddress, 0, 0);
		this.add(jpButton, 0, 1);
		jbtAdd.setOnAction(ae);
		jbtFirst.setOnAction(ae);
		jbtNext.setOnAction(ae);
		jbtPrevious.setOnAction(ae);
		jbtLast.setOnAction(ae);
		jbtFirst.Execute();
		jbtSort1.setOnAction(ae);
		jbtSort2.setOnAction(ae);
		jbtIter.setOnAction(ae);
	}

	public void actionHandled(ActionEvent e) {
		((Command) e.getSource()).Execute();
	}

	public void SetName(String text) {
		jtfName.setText(text);
	}

	public void SetStreet(String text) {
		jtfStreet.setText(text);
	}

	public void SetCity(String text) {
		jtfCity.setText(text);
	}

	public void SetState(String text) {
		jtfState.setText(text);
	}

	public void SetZip(String text) {
		jtfZip.setText(text);
	}

	public String GetName() {
		return jtfName.getText();
	}

	public String GetStreet() {
		return jtfStreet.getText();
	}

	public String GetCity() {
		return jtfCity.getText();
	}

	public String GetState() {
		return jtfState.getText();
	}

	public String GetZip() {
		return jtfZip.getText();
	}
}

interface Command {
	public void Execute();
}

class CommandButton extends Button implements Command {
	public final static int NAME_SIZE = 32;
	public final static int STREET_SIZE = 32;
	public final static int CITY_SIZE = 20;
	public final static int STATE_SIZE = 10;
	public final static int ZIP_SIZE = 5;
	public final static int RECORD_SIZE = (NAME_SIZE + STREET_SIZE + CITY_SIZE + STATE_SIZE + ZIP_SIZE);
	protected AddressBookPane p;
	protected RandomAccessFile raf;

	public CommandButton(AddressBookPane pane, RandomAccessFile r) {
		super();
		p = pane;
		raf = r;
	}

	public void Execute() {
	}

	/** Write a record at the end of the file */
	public void writeAddress() {
		try {
			raf.seek(raf.length());
			FixedLengthStringIO.writeFixedLengthString(p.GetName(), NAME_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetStreet(), STREET_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetCity(), CITY_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetState(), STATE_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetZip(), ZIP_SIZE, raf);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/** Read a record at the specified position */
	public void readAddress(long position) throws IOException {
		raf.seek(position);
		String name = FixedLengthStringIO.readFixedLengthString(NAME_SIZE, raf);
		String street = FixedLengthStringIO.readFixedLengthString(STREET_SIZE, raf);
		String city = FixedLengthStringIO.readFixedLengthString(CITY_SIZE, raf);
		String state = FixedLengthStringIO.readFixedLengthString(STATE_SIZE, raf);
		String zip = FixedLengthStringIO.readFixedLengthString(ZIP_SIZE, raf);
		p.SetName(name);
		p.SetStreet(street);
		p.SetCity(city);
		p.SetState(state);
		p.SetZip(zip);
	}

	public void sort(Comparator<String> comparator, long position) {
		String s1, s2, temp;
		final long DOUBLE_REC_SIZE = RECORD_SIZE * 2;
		long p1, p2 = 0;
		try {
			for (long i = 0; i < raf.length(); i += DOUBLE_REC_SIZE) {
				boolean change = false;
				raf.seek(i);
				s1 = FixedLengthStringIO.readFixedLengthString(RECORD_SIZE, raf);
				temp = s1;
				p1 = raf.getFilePointer() - DOUBLE_REC_SIZE;
				for (long j = DOUBLE_REC_SIZE; j < raf.length() - i; j += DOUBLE_REC_SIZE) {
					s2 = FixedLengthStringIO.readFixedLengthString(RECORD_SIZE, raf);
					if (comparator.compare(s1, s2) > 0) {
						p2 = raf.getFilePointer() - DOUBLE_REC_SIZE;
						s1 = s2;
						change = true;
					}
				}
				if (change == true) {
					raf.seek(p1);
					FixedLengthStringIO.writeFixedLengthString(s1, RECORD_SIZE, raf);
					raf.seek(p2);
					FixedLengthStringIO.writeFixedLengthString(temp, RECORD_SIZE, raf);
				}
			}
			raf.seek(position);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ListIterator<String> listIterator(int i, RandomAccessFile raf, int size) throws IOException {
		return new listIter(i, raf, size);
	}
}

class AddButton extends CommandButton {
	public AddButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Add");
	}

	@Override
	public void Execute() {
		writeAddress();
	}
}

class NextButton extends CommandButton {
	public NextButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Next");
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = raf.getFilePointer();
			if (currentPosition < raf.length())
				readAddress(currentPosition);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class PreviousButton extends CommandButton {
	public PreviousButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Previous");
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = raf.getFilePointer();
			if (currentPosition - 2 * 2 * RECORD_SIZE >= 0)
				readAddress(currentPosition - 2 * 2 * RECORD_SIZE);
			else
				;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class LastButton extends CommandButton {
	public LastButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Last");
	}

	@Override
	public void Execute() {
		try {
			long lastPosition = raf.length();
			if (lastPosition > 0)
				readAddress(lastPosition - 2 * RECORD_SIZE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class FirstButton extends CommandButton {
	public FirstButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("First");
	}

	@Override
	public void Execute() {
		try {
			if (raf.length() > 0)
				readAddress(0);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class Sort1Button extends CommandButton {
	public Sort1Button(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Sort1");
	}

	@Override
	public void Execute() {
		try {
			Comparator<String> nameComparator = (s1, s2) -> {
				String n1 = s1.substring(0, NAME_SIZE).trim();
				String n2 = s2.substring(0, NAME_SIZE).trim();
				return n1.compareTo(n2);
			};
			long pos = raf.getFilePointer();
			sort(nameComparator, pos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Sort2Button extends CommandButton {
	public Sort2Button(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Sort2");
	}

	@Override
	public void Execute() {
		try {
			Comparator<String> zipComparator = (s1, s2) -> {
				Integer z1 = Integer.parseInt(s1.substring(RECORD_SIZE - ZIP_SIZE, RECORD_SIZE).trim());
				Integer z2 = Integer.parseInt(s2.substring(RECORD_SIZE - ZIP_SIZE, RECORD_SIZE).trim());
				return z1.compareTo(z2);
			};
			long pos = raf.getFilePointer();
			sort(zipComparator, pos);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class IterButton extends CommandButton {
	private Map<String, String> m;
	private boolean flag = false;
	private ListIterator<String> lit;

	public IterButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Iter");
		try {
			lit = listIterator(0, raf, RECORD_SIZE * 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Execute() {
		try {
			if (!flag) {
				flag = true;
				m = new LinkedHashMap<>();
				while (lit.hasNext()) {
					String value = (String) lit.next();
					String key = value.substring(0, RECORD_SIZE - ZIP_SIZE);
					m.put(key, value);
				}
				raf.seek(0);
				Iterator<String> it = m.values().iterator();
				update(lit, it);
			} else { // Second click and onward.
				Comparator<String> streetComp = (a1, a2) -> {
					String s1 = a1.substring(NAME_SIZE, NAME_SIZE + STREET_SIZE).trim();
					String s2 = a2.substring(NAME_SIZE, NAME_SIZE + STREET_SIZE).trim();
					int res = s1.compareTo(s2);
					if (res >= 0) {
						return 1;
					} else {
						return -1;
					}
				};
				TreeSet<String> set = new TreeSet<>(streetComp);
				set.addAll(m.values());
				Iterator<String> it = set.iterator();
				update(lit, it);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void update(ListIterator<String> lit2, Iterator<String> it) {
		while (lit.hasPrevious()) {
			lit.previous();
			lit.remove();
		}
		while (it.hasNext()) {
			lit.add(it.next());
		}
	}
}

class listIter implements ListIterator<String> {
	private int cur;
	private int last;
	private int size;
	private long numElements;
	private RandomAccessFile raf;

	public listIter(int i, RandomAccessFile raf, int size) throws IOException {
		this.raf = raf;
		this.cur = i;
		this.size = size;
		this.numElements = raf.length() / size;
		this.last = -1;
	}

	@Override
	public void add(String s) {
		if (cur < 0 || cur > numElements) {
			throw new IndexOutOfBoundsException();
		}

		try {
			ArrayList<String> arr = binToArr();
			arr.add(cur, s);
			arrToBin(arr);
			numElements++;
			cur++;
			last = -1;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove() {
		if (last == -1) {
			throw new IllegalStateException();
		}
		try {
			ArrayList<String> arr = binToArr();
			arr.remove(last);
			arrToBin(arr);
			numElements--;
			cur = last;
			last = -1;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasNext() {
		return cur < numElements;
	}

	@Override
	public boolean hasPrevious() {
		return cur > 0;
	}

	@Override
	public String next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		String temp = null;
		try {
			raf.seek(cur * size);
			temp = FixedLengthStringIO.readFixedLengthString(size / 2, raf);
			last = cur;
			cur++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public int nextIndex() {
		return cur;
	}

	@Override
	public String previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		cur--;
		String temp = null;
		try {
			raf.seek(cur * size);
			temp = FixedLengthStringIO.readFixedLengthString(size / 2, raf);
			last = cur;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public int previousIndex() {
		return cur - 1;
	}

	@Override
	public void set(String s) {
		if (last == -1) {
			throw new IllegalStateException();
		}
		try {
			raf.seek(last * size);
			FixedLengthStringIO.writeFixedLengthString(s, size, raf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void arrToBin(ArrayList<String> arr) throws IOException {
		raf.seek(0);
		raf.setLength(0);
		for (String string : arr) {
			FixedLengthStringIO.writeFixedLengthString(string, size / 2, raf);
		}
	}

	private ArrayList<String> binToArr() throws IOException {
		ArrayList<String> arr = new ArrayList<>();
		raf.seek(0);
		while (raf.getFilePointer() < raf.length()) {
			arr.add(FixedLengthStringIO.readFixedLengthString(size / 2, raf));
		}
		return arr;
	}

}
