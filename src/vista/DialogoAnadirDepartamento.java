package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;

import controlador.Controlador;
import modelo.Centro;
import modelo.Departamento;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class DialogoAnadirDepartamento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodDpto;
	private JTextField txtNombre;
	private final ButtonGroup grupoDireccion = new ButtonGroup();
	private Controlador controlador;
	private JComboBox comboBox;
	private JRadioButton rdbPropiedad;
	private JRadioButton rdbFunciones;
	private JSpinner spinner;


	/**
	 * Create the dialog.
	 */
	public DialogoAnadirDepartamento() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "Detalles del departamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow][grow][grow]", "[][][][][][][][][]"));
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(DialogoAnadirDepartamento.class.getResource("/images/editar32.png")));
			lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel, "cell 0 0 1 5");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Código:");
			lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel_1, "cell 1 0,alignx trailing");
		}
		{
			txtCodDpto = new JTextField();
			contentPanel.add(txtCodDpto, "cell 2 0 2 1,growx");
			txtCodDpto.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Centro:");
			lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel_2, "cell 1 2,alignx trailing");
		}
		{
			comboBox = new JComboBox();
			contentPanel.add(comboBox, "cell 2 2 2 1,growx");
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Tipo Dirección:");
			lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel_3, "cell 1 4,alignx right");
		}
		{
			rdbPropiedad = new JRadioButton("Propiedad");
			rdbPropiedad.setSelected(true);
			grupoDireccion.add(rdbPropiedad);
			rdbPropiedad.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(rdbPropiedad, "cell 2 4,growx");
		}
		{
			rdbFunciones = new JRadioButton("En funciones");
			grupoDireccion.add(rdbFunciones);
			rdbFunciones.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(rdbFunciones, "cell 3 4,growx");
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Presupuesto:");
			lblNewLabel_4.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel_4, "cell 1 6,alignx right");
		}
		{
			spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(5, 1, 100, 1));
			contentPanel.add(spinner, "cell 2 6");
		}
		{
			JLabel lblNewLabel_5 = new JLabel("(en miles de €)");
			lblNewLabel_5.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel_5, "cell 3 6");
		}
		{
			JLabel lblNewLabel_6 = new JLabel("Nombre:");
			lblNewLabel_6.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel_6, "cell 1 8,alignx trailing");
		}
		{
			txtNombre = new JTextField();
			contentPanel.add(txtNombre, "cell 2 8 2 1,growx");
			txtNombre.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						recogerDatos();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}


	protected void recogerDatos() {
		try {
			int codigo = Integer.parseInt(txtCodDpto.getText());
			
			Centro c = (Centro) comboBox.getSelectedItem();
			
			String tipoDir="P";
			if (rdbFunciones.isSelected()) {
				tipoDir="F";
			}
			int presupuesto = (int) spinner.getValue();
			String nombre= txtNombre.getText();
			if (nombre==null || nombre.isBlank()) {
				JOptionPane.showMessageDialog(contentPanel,
						"Debne introducir uin nombre para el departamento");
				return;
			}
			
			Departamento dpto = new Departamento(codigo, c.getCod_centro(), 
					tipoDir, presupuesto, nombre);
			
			controlador.insertarDepartamento(dpto);
			
		} catch(NumberFormatException e1 ) {
			JOptionPane.showMessageDialog(contentPanel,
					"Debe introducir un código de depatametno numérico");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(contentPanel,
					"Error al insertar el departamento");
		}
		
		
		
		
		
	}


	public void setControlador(Controlador controlador) {
		this.controlador=controlador;
	}


	public void setListaCentros(ArrayList<Centro> lista) {
		comboBox.removeAllItems();
		for (Centro centro : lista) {
			comboBox.addItem(centro);
		}
		
	}

}
