let PRNG = System.Random()

let l =fsi.CommandLineArgs.[1]|>int
let filename =fsi.CommandLineArgs.[2]

type node = int * int  //value * name

let flip i =
    match i with
        | 1 -> -1
        | -1 -> 1
        |_ -> failwith "Flipper only takes +/- 1"

let rec name d =
    if d=0 then "a" else
        match d%10 with
            | 0 -> "a" + name (d/10)
            | 1 -> "b" + name (d/10)
            | 2 -> "c" + name (d/10)
            | 3 -> "d" + name (d/10)
            | 4 -> "e" + name (d/10)
            | 5 -> "f" + name (d/10)
            | 6 -> "g" + name (d/10)
            | 7 -> "h" + name (d/10)
            | 8 -> "i" + name (d/10)
            | 9 -> "j" + name (d/10)
            | _ -> failwith "MODULO OPERATION BROKEN"

let level n =
    let sum =[1..n]|> List.sum
    [0..n] |> List.map (fun i -> sum+i)

let nodes = [0..l] |> List.map level
let sparsity = 30

let genedges l =
    [for node in level l do
           let coin = PRNG.Next(100)
           if coin <= sparsity then () else yield (node,node+l+1)
           let coin = PRNG.Next(100)
           if coin <= sparsity then () else yield (node,node+l+2)]

let edges = [0..l]|> List.map genedges
let finale = edges |> List.rev |> List.head |> List.map snd |> List.distinct |> List.rev
let goal = List.max finale + 1
let goal_edges = finale |> List.map (fun n -> (n,goal))

let final_nodes = nodes |> List.concat |> List.rev |> List.append finale |> List.append [goal] |> List.rev
let final_edges = edges |> List.concat |> List.rev |> List.append goal_edges |> List.rev
let n = (final_nodes|>List.length)
let red_nodes = final_nodes |> List.sortBy(fun _ -> PRNG.Next(100)) |> List.take (PRNG.Next(n/2))

let G = System.IO.File.CreateText(sprintf "graphs/%s.txt" filename)

fprintfn G "%d %d %d" n (final_edges |> List.length) (red_nodes |> List.length)
fprintfn G "%d %d" 0 goal

List.iter (fun i -> fprintfn G "%d %s" i (if (List.contains i red_nodes) then "*" else ""))  final_nodes
List.iter (fun (n1,n2) -> fprintfn G "%d -> %d" n1 n2) final_edges

G.Close()

if n<81 then

    let mp =System.IO.File.CreateText(sprintf "figures/%s.mp" filename)

    fprintfn mp "%s" "verbatimtex\n%&latex\n\\documentclass{article}\n\\usepackage{amsmath}\n\\usepackage{color}\n\\newcommand{\\red}[1]{{\\color{red} #1}}\n\\begin{document}\netex"
    fprintfn mp "%s" "input ../metagraph-0.2/metagraph"
    fprintfn mp "%s" "beginfig(0)\nnoderadius:=0.3cm;"
//Declare nodes
    List.iter (fun i -> fprintfn mp "node.%s;" (name  i)) final_nodes
    List.iter (fun i -> fprintfn mp "node.%s;" (name  i)) (nodes|> List.concat)
//Offset nodes from each other
    let genoffset l =
        for node in level l do
           let child1 = node+l+1
           let child2 = node+l+2
           fprintfn mp "%s.c=%s.c + (-1cm,-1cm);" (name child1) (name node)
           fprintfn mp "%s.c=%s.c + (1cm, -1cm);" (name child2) (name node)
    for i in [0..l] do genoffset i
    fprintfn mp "%s.c=%s.c + (-4cm,-1cm);" (name goal) (name (finale |> List.max))
//Draw nodes
    fprintf mp "%s %s" "drawunboxed(" (name final_nodes.[0])
    [1..(n-1)] |> List.iter (fun i -> fprintf mp ", %s" (name final_nodes.[i]))
    fprintfn mp ")"
//Draw edges
    for (a,b) in final_edges
        do
            if b = goal then
               let lastto = finale |> List.take 2
               if List.contains a lastto
               then
                   fprintfn mp "drawarrow arc(%s)({dir 210}..)(%s) dashed evenly withcolor 0.5;" (name a) (name b)
               else
                    fprintfn mp "drawarrow arc(%s)({dir -20}..)(%s) dashed evenly withcolor 0.5;" (name a) (name b)
            else fprintfn mp "drawarrow arc(%s)({down}..)(%s) dashed evenly withcolor 0.5;" (name a) (name b)
    fprintfn mp "endfig;\nverbatimtex\n\\end{document}\netex\nbye"
    mp.Close()

