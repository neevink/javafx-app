package com.neevin;

/*
Лабораторная 5
Вариант: 313087

Лабораторная 6
Варинат: 313387

 */


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientMain {
    // Поскольку ip это localhost, а вот порт меняется, тогда может понадобиться возможность сменить порт
    private static int port = 12345;

    public static void main(String[] args) {
        // Если предан другой порт в командной строке
        if(args.length == 1){
            try{
                port = Integer.parseInt(args[0]);
            }
            catch (Exception e){
                System.out.println("Что-то не получилось спарсить порт из агрументов командной строки, используется стандартный");
            }
        }

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            byte[] b = sc.nextLine().getBytes(StandardCharsets.UTF_8);
            try{
                Socket socket = new Socket("127.0.0.1", port);
                System.out.println("Успешное подключение по адресу 127.0.0.1:" + port);

                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(b);

                InputStream inputStream = socket.getInputStream();


                // ТУТ ждём пока придёт ответ

            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }


    }
}
