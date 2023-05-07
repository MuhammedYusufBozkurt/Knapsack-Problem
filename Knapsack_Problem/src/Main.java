import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //-----------------------------------------------------------------------------------------------
        //DOSYAYI İÇE AKTARMA VE DİZİYE ATAMA
        //-----------------------------------------------------------------------------------------------
        int boyut = 0;
        int topweight = 0;
        File dosya = new File("C:\\Sırt Çantası Problemi\\\\ks_10000_0.txt");
        if(dosya.exists())
        {
            System.out.println("dosya bulundu");
        }
        else
        {
            System.out.println("dosya bulunamadı");
        }


        Scanner scanner = new Scanner(dosya);
        boyut = scanner.nextInt();
        topweight = scanner.nextInt();

        int [][] knapsackVW = new int [boyut][2];

        int i = -1;
        int j = 0;

        System.out.println("Toplam ağırlık : " + topweight);
        while (scanner.hasNext())
        {
            if( i == -1)
            {

                System.out.println("Dosya boyutu : " + boyut);
                i++;
            }

            knapsackVW[i][0] = scanner.nextInt();
            knapsackVW[i][1] = scanner.nextInt();


            System.out.println((i+1)  + ". ELEMAN " + knapsackVW[i][0] + " " + knapsackVW[i][1]);
            i++;
        }
        //-----------------------------------------------------------------------------------------------
        //TABLO OLUŞTURMA
        //-----------------------------------------------------------------------------------------------

        if (boyut < 1200)
        {
            int [][] tablo = new int[boyut + 1][topweight + 1];

            for (int n = 0 ; n < topweight + 1; n++)
            {
                tablo[0][n] = 0;
            }

            tablo = TabloOlustur1(boyut,topweight,tablo,knapsackVW);


            //-----------------------------------------------------------------------------------------------
            //TABLOYU EKRANA YAZDIRMA
            //-----------------------------------------------------------------------------------------------
            /*
            for (int n = 0; n < boyut + 1 ; n++)
            {
                for (int w = 0 ; w < topweight + 1 ; w++)
                {
                    if(w < topweight)
                    {
                        System.out.print(tablo[n][w] +",   ");
                    }
                    else
                        System.out.println(tablo[n][w]);
                }
            }
             */
            //-----------------------------------------------------------------------------------------------
            //TABLODAN DEĞERLERİ BULMA
            //-----------------------------------------------------------------------------------------------


            int [] sonuc = new int[boyut+1];
            for (int f = 0; f < sonuc.length ; f++)
            {
                sonuc[f] = 0;
            }
            for (int f = 0; f < topweight +1; f++)
            {
                tablo[0][f] = 0;
            }

            sonuc = Find1(boyut,topweight,sonuc,knapsackVW,tablo);

            System.out.println(tablo[boyut][topweight]);
            for (int q = 0; q < sonuc.length-1; q++)
            {
                System.out.print(sonuc[q] + " ");
            }



            int toplamagirlik = 0;
            int toplamdeger = 0;

            for (int y = 0 ; y < boyut ; y++ )
            {
                if (sonuc[y] == 1)
                {
                    toplamdeger += knapsackVW[y][0];
                    toplamagirlik +=knapsackVW[y][1];
                }
            }

            System.out.println("\n" + "TOPLAM AĞIRLIK : " + toplamagirlik);

            System.out.println("TOPLAM DEĞER : " + toplamdeger);

        }
        else
        {
            //-----------------------------------------------------------------------------------------------
            //TABLO OLUŞTURMA
            //10000 ELEMANLIK DOSYAYI 10 EŞİT PARÇAYA BÖLDÜM HER PARÇA İÇİN
            //DEĞERİ BULDUM VE KARŞILAŞTIRDIM EN BÜYÜK OLANI SONUÇ OLARAK ALDIM
            //-----------------------------------------------------------------------------------------------
            int [][] tablo = new int[1001][topweight + 1];

            for (int n = 0 ; n < topweight + 1; n++)
            {
                tablo[0][n] = 0;
            }
            int aralik = 0;
            int maxdeger = 0;
            for(int a = 0 ; a < 10 ; a++)
            {
                tablo = TabloOlustur2(a,topweight,tablo,knapsackVW);
                if (maxdeger < tablo[1000][topweight])
                {
                    aralik = a;
                    maxdeger = tablo[1000][topweight];
                }
            }

            tablo = TabloOlustur2(aralik,topweight,tablo,knapsackVW);

            //--------------------------------------------------------------------------------------------
            // TABLODAN HANGİ DEĞERLERİ ALDIĞIMIZI BULMA
            //--------------------------------------------------------------------------------------------

            int [] sonuc = new int[1001];
            for (int f = 0; f < 1001 ; f++)
            {
                sonuc[f] = 0;
            }
            for (int f = 0; f < topweight +1; f++)
            {
                tablo[0][f] = 0;
            }
            int f = 0;
            int [][] knapsackvw = new int[1000][2];
            for (int t = 2000; t < 3000; t++)
            {

                knapsackvw[f][0] = knapsackVW[t][0];
                knapsackvw[f][1] = knapsackVW[t][1];
                f++;
            }

            sonuc = Find2(1000,topweight,sonuc,knapsackvw,tablo);


            for (int q = 0; q < sonuc.length-1; q++)
            {
                System.out.print(sonuc[q] + " ");
            }

            int toplamagirlik = 0;
            int toplamdeger = 0;

            for (int y = 1 ; y<1000 ; y++ )
            {
                if (sonuc[y] == 1)
                {
                    toplamdeger += knapsackvw[y][0];
                    toplamagirlik +=knapsackvw[y][1];
                }
            }

            System.out.println("\n" + "TOPLAM AĞIRLIK : " + toplamagirlik);

            System.out.println("TOPLAM DEĞER : " + toplamdeger);

        }

    }

    //-----------------------------------------------------------------------------------------------
    //4-19-200 İÇİN FİND FONKSİYONU
    //-----------------------------------------------------------------------------------------------
    public static int[] Find1(int n , int w , int [] find, int [][] knapsack , int [][] tablo)
    {
        if(n == 0)
        {
            //System.out.println("ıf calıstı");
            return find;
        }
        else if (tablo[n][w] == tablo[n-1][w])
        {
            n-- ;
            //System.out.println("1. elseıf calıstı");
            return Find1(n,w,find,knapsack,tablo);
        }
        else if (tablo[n][w] == tablo[n-1][w-1])
        {
            n-- ;
            w-- ;
            //System.out.println("2. elseıf calıstı");
            return Find1(n,w,find,knapsack,tablo);
        }
        else if (tablo[n][w] == tablo[n][w-1])
        {
            w-- ;
            //System.out.println("3. elseıf calıstı");
            return Find1(n,w,find,knapsack,tablo);
        }
        else
        {
            find[n-1] = 1;
            n = n-1;
            w = w - knapsack[n][1];
            //System.out.println("else calıstı");
            return Find1(n,w,find,knapsack,tablo);

        }
    }

    //-----------------------------------------------------------------------------------------------
    //4-19-200 İÇİN TABLO OLUŞTURMA FONKSİYONU
    //-----------------------------------------------------------------------------------------------
    public static int [][] TabloOlustur1(int boyut, int agırlık, int[][] tablo, int [][] knapsack)
    {
        for (int n = 1; n < boyut + 1; n++)
        {
            for (int w = 1 ; w < agırlık + 1; w++)
            {
                if (knapsack[n-1][1] > w)
                {
                    tablo[n][w] = tablo[n-1][w];
                }
                else
                {
                    if (tablo[n-1][w] > knapsack[n-1][0] + tablo[n-1][w - knapsack[n-1][1]])
                    {
                        tablo[n][w] = tablo[n-1][w];
                    }
                    else
                    {
                        tablo[n][w] = knapsack[n-1][0] + tablo[n-1][w - knapsack[n-1][1]];
                    }
                }
            }
        }
        return tablo;
    }

    //-----------------------------------------------------------------------------------------------
    //10000 İÇİN FİND FONKSİYONU
    //-----------------------------------------------------------------------------------------------
    public static int[] Find2(int n , int w , int [] find, int [][] knapsack , int [][] tablo)
    {
        if(n == 0)
        {
            //System.out.println("ıf calıstı");
            return find;
        }
        else if (tablo[n][w] == tablo[n-1][w])
        {
            n-- ;
            //System.out.println("1. elseıf calıstı");
            return Find2(n,w,find,knapsack,tablo);
        }
        else if (tablo[n][w] == tablo[n-1][w-1])
        {
            n-- ;
            w-- ;
            //System.out.println("2. elseıf calıstı");
            return Find2(n,w,find,knapsack,tablo);
        }
        else if (tablo[n][w] == tablo[n][w-1])
        {
            w-- ;
            //System.out.println("3. elseıf calıstı");
            return Find2(n,w,find,knapsack,tablo);
        }
        else
        {
            find[n-1] = 1;
            n = n-1;
            w = w - knapsack[n][1];
            //System.out.println("else calıstı");
            System.out.println(n);
            return Find2(n,w,find,knapsack,tablo);


        }

    }

    //-----------------------------------------------------------------------------------------------
    //10000 İÇİN TABLO OLUŞTURMA FONKSİYONU
    //-----------------------------------------------------------------------------------------------
    public static int [][] TabloOlustur2(int aralik, int agırlık, int[][] tablo, int [][] knapsack)
    {
        for (int n = 1; n < 1001; n++)
        {
            for (int w = 1 ; w < agırlık + 1; w++)
            {
                if (knapsack[n + (aralik*1000) - 1][1] > w)
                {
                    tablo[n][w] = tablo[n-1][w];
                }
                else
                {
                    if (tablo[n-1][w] > knapsack[n + aralik*1000-1][0] + tablo[n-1][w - knapsack[n + aralik*1000-1][1]])
                    {
                        tablo[n][w] = tablo[n-1][w];
                    }
                    else
                    {
                        tablo[n][w] = knapsack[n + aralik*1000-1][0] + tablo[n-1][w - knapsack[n + aralik*1000-1][1]];
                    }
                }
            }
        }
        return tablo;
    }

}