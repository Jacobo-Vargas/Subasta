package co.edu.uniquindio.subasta.model;

import java.io.IOException;

import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import static java.lang.String.valueOf;

public class GenerarReportePdf {

    public static void generarPDFVenta(PujaDto pujaDto, AnuncioDto anuncioDto, CompradorDto compradorDto) throws IOException {

        String nombreArchivo = "Venta_" + anuncioDto.nombre() + ".pdf";

        // Agrega la ruta absoluta en la siguiente línea
        String rutaEscritorio = System.getenv("HOME") + "/Escritorio/";
        String rutaArchivo = rutaEscritorio + nombreArchivo;

        try (PdfWriter writer = new PdfWriter(rutaArchivo);

             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Agregar contenido al PDF
            document.add(new Paragraph("**************************************************************************************"));
            document.add(new Paragraph("**                             Información de la Venta                                               **"));
            document.add(new Paragraph("**************************************************************************************"));
            document.add(new Paragraph("    Cliente: " + compradorDto.nombre()+" "+ compradorDto.apellido()));
            document.add(new Paragraph("    Cedula: "+ compradorDto.cedula()));
            document.add(new Paragraph("    Telefono: "+ compradorDto.telefono() ));
            document.add(new Paragraph("    Direccion: "+ pujaDto.direccion()));
            document.add(new Paragraph("    Producto: "+ anuncioDto.producto().nombre()));
            document.add(new Paragraph("    Valor: "+ pujaDto.oferta()));
            document.add(new Paragraph("**************************************************************************************"));


//            // Agregar más información según necesites
//
//            // Crear una tabla para mostrar los detalles de la venta
//            Table table = new Table(4); // 4 columnas para cantidad, coddigo nobre de producto y subtotal
//            table.addCell("Cantidad");
//            table.addCell("Codigo producto   ");
//            table.addCell("Nombre del producto");
//            table.addCell("Subtotal   ");
//            table.addFooterCell("total a pagar: "+ venta.calcularTotal());
//
//            for (DetalleVenta detalle : venta.getDetalleVenta()) {
//                table.addCell(valueOf(detalle.getCantidadProductos()));
//                table.addCell(detalle.getProductoVendido());
//                table.addCell(detalle.obtenerNombreProducto());
//                table.addCell(valueOf(detalle.getSubtotal()));
//            }
//
//
//            document.add(table);
        }
    }
}
