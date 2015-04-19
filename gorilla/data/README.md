# Protein sequence descriptions

## Sequences

There are two input files containing protein sequences.
The first, “Toy-FASTAs-in.txt” contains very short proteins from three fictional species that you can use for testing.

    >Sphinx
    KQRK
    >Bandersnatch
    KAK
    >Snark
    KQRIKAAKABK

The expected alignments of these three sequences are in “Toy-FASTAs-out.txt”.

    Sphinx--Snark: -8
    KQR-------K
    KQRIKAAKABK
    Sphinx--Bandersnatch: 5
    KQRK
    K-AK
    Snark--Bandersnatch: -18
    KQRIKAAKABK
    -------KA-K


The second input file, “HbB_FASTAs-in.txt” contains proper data from a protein sequence database.
Here are the first few lines:

    >Human 2144721 HBHU 4HHB
    MVHLTPEEKSAVTALWGKVNVDEVGGEALGRLLVVYPWTQRFFESFGDLSTPDAVMGNPKVKAHGKKVLG
    AFSDGLAHLDNLKGTFATLSELHCDKLHVDPENFRLLGNVLVCVLAHHFGKEFTPPVQAAYQKVVAGVAN
    ALAHKYH
    >Human-sickle 2392691 2HBS
    VHLTPVEKSAVTALWGKVNVDEVGGEALGRLLVVYPWTQRFFESFGDLSTPDAVMGNPKVKAHGKKVLGA
    FSDGLAHLDNLKGTFATLSELHCDKLHVDPENFRLLGNVLVCVLAHHFGKEFTPPVQAAYQKVVAGVANA
    LAHKYH
    ...


The entry for Human takes up four lines.
First, the name of the species appears directly after the “>” sign.
The protein itself is described on the next three lines,  from “MVHL” to “KYH”.
(In principle, sequences can be any number of lines.)
Ignore things like “2144721 HBHU 4HHB” — I have no idea what that stuff even means.

There is a corresponding output file.
You can see if Human is closer to Gorilla than Sea Cucumber.
(Maybe you had an intuition about that already.
Now you know.)
Note that in principle, there might be more than one optimal alignment.

## Penalties

A third file, “BLOSUM62.txt” contains a matrix of scores.
You have to look at it for a while to make sense of it.
BLOSUM is short for BLOcks SUbstitution Matrix.
It’s used in a lot of computational biology software; you can read [the Wikipedia article on BLOSUM](http://en.wikipedia.org/wiki/BLOSUM) if you want to learn more.
