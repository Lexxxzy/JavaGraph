package com.company;
import java.util.*;

public class Main
{
    static int[][] graph =  {{1, 0, 0, 0, 0, 1, 0, 0, 1},
                             {1, 1, 0, 1, 1, 0, 0, 0, 0},
                             {0, 1, 1, 0, 0, 0, 0, 0, 1},
                             {0, 0, 1, 1, 0, 0, 1, 0, 0},
                             {0, 0, 0, 0, 0, 0, 1, 1, 0},
                             {0, 0, 0, 0, 0, 0, 0, 1, 0},
                             {0, 0, 0, 0, 1, 1, 0, 0, 0}   };
    static int[] used, d, up;
    //d[]- глубина текущей вершины
    //up[] - минимально возможная вершина в которую мы можем опуститься
    //из этой вершины в процессе обхода в глубину (по умолчанию текущая глубина)
    static int time;
    static Set<Integer> ArtPoints = new TreeSet<Integer>(); //точки сочленения


//Поиск точек сочленения совершаем методом поиска в глубину.
// Для каждой вершины v вычисляем метки d[v] / up[v].
static void dfs (int v, int p)
    {
        int i, to, children;
        used[v] = 1; //уже проийденная вершина
        d[v] = up[v] = time++;

        children = 0;

        for (i = 0; i < 9; i++)  //идем по массиву и ищем какие есть дуги у заданной вершины
            // (Перебираем вершины to, достижимые из v.)
        {
            if (graph[v][i] == 1) //если дуга есть
            {
                for (int j = 0; j < 7; j++)
                { //ищем в какую вершину идет дуга
                    if (graph[j][i]==1 && j!=v) //если дуга идет в какую-то вершину и она не равна сама себе
                    {
                        to = j; //находим к какой вершине идет дуга
                        if (used[to] == 1)
                        { //Если вершина to уже посещена, то (v, to) обратное ребро. Пересчитаем значение up[v]
                            up[v] = Math.min(up[v], d[to]);

                        }

                        else //поиск в глубину из вершины to
                        {
                            dfs (to, v);
                            up[v] = Math.min(up[v], up[to]);
                            if ((up[to] >= d[v]) && (p != -1)) ArtPoints.add(v);

                            children++; //Подсчитаем количество вершин to, в которые поиск в глубину заходит из вершины v.
                        }

                    }
                }
            }

        }
        //Если v корень (p = -1) и количество его сыновей в дереве поиска больше 1, то вершина v является точкой сочленения.
        if ((p == -1) && (children > 1)) ArtPoints.add(v);
    }


    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        used = new int[7+1];
        d = new int[7+1];
        up = new int[7+1];

        time = 1;
        for(int i = 0; i <= 6; i++)
            if (used[i] == 0) dfs(i,-1);


        System.out.println("Точки сочленения: ");
        for(int i : ArtPoints)
            System.out.println(++i);


    }
}