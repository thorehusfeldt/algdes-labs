let PRNG = System.Random()

let n =fsi.CommandLineArgs.[1]|>int
let filename =fsi.CommandLineArgs.[2]

type node = int

let flip i =
    match i with
        | 1 -> -1
        | -1 -> 1
        |_ -> failwith "Flipper only takes +/- 1"


let nodes = [1..2*n]|> List.sortBy (fun _-> PRNG.Next(2*n)) |> List.take n

let rec genedges nodes =
    match nodes with
        | [] -> Seq.empty
        | n::[] -> Seq.empty
        | n::ns ->
            let edges = seq{ for b in ns do
                             match n<b with
                             |true ->
                                yield (n,b)
                             |false -> ()}
            Seq.append edges (genedges ns)

let G = System.IO.File.CreateText(sprintf "graphs/%s.txt" filename)
let edges = genedges nodes |> Seq.toList

fprintfn G "%d %d %d" n (edges |> List.length) (nodes |> List.filter (fun v -> v%2 =1) |> List.length)
fprintfn G "%d %d" (nodes|> List.head) (nodes |> List.rev |> List.head)
List.iter (fun i -> fprintfn G "%d %s" i (if (i % 2 = 1) then "*" else ""))  nodes
edges |> List.iter (fun (n1,n2) -> fprintfn G "%d -> %d" n1 n2)

G.Close()

if n<51 then

    let mp =System.IO.File.CreateText(sprintf "figures/%s.mp" filename)

    fprintfn mp "%s" "verbatimtex\n%&latex\n\\documentclass{article}\n\\usepackage{amsmath}\n\\usepackage{color}\n\\newcommand{\\red}[1]{{\\color{red} #1}}\n\\begin{document}\netex"
    fprintfn mp "%s" "input ../metagraph-0.2/metagraph"
    fprintfn mp "%s" "beginfig(0)\nnoderadius:=0.3cm;"
//Declare nodes
    List.iter (fun i -> fprintfn mp "node.n%d(btex %s etex);" i
                              (if i % 2 = 1 then (sprintf "\\red{%d}" i) else (sprintf "%d" i))) nodes
//Offset nodes from each other
    [1..(n-1)] |> List.iter (fun index -> fprintfn mp "n%d.c = n%d.c + (2cm,0);" nodes.[index] nodes.[index - 1])
//Draw nodes
    fprintf mp "%s n%d" "drawunboxed(" nodes.[0]
    [1..(n-1)] |> List.iter (fun i -> fprintf mp ", n%d" nodes.[i])
    fprintfn mp ")"
//Draw edges
    let rec mpedges nodes d =
        let d = flip d
        match nodes with
            | [] -> ()
            | n::[] -> ()
            | n::ns ->
                let mutable i = 1
                seq{ for b in ns do
                     match n<b with
                        |true ->
                           fprintfn mp "drawarrow arc(n%d)({dir %d}..)(n%d);" n (i*d*5) b
                           i <- i+1
                           yield (n,b)
                        |false -> ()} |> Seq.toList |> ignore
                mpedges ns d
    mpedges nodes -1
    fprintfn mp "endfig;\nverbatimtex\n\\end{document}\netex\nbye"
    mp.Close()

