%  METAGRAPH 0.2: simple macros to draw (un)directed graphs with METAPOST.
%
%	Copyright (C) 2005, 2006 Sebastiano Vigna
%
%	This program is free software; you can redistribute it and/or modify it
%	under the terms of the GNU General Public License as published by the
%	Free Software Foundation; either version 2, or (at your option) any
%	later version.
%	
%	This program is distributed in the hope that it will be useful, but
%	WITHOUT ANY WARRANTY; without even the implied warranty of
%	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
%	General Public License for more details.
%	
%	You should have received a copy of the GNU General Public License along
%	with this program; see the file COPYING.  If not, write to the Free
%	Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
%	02111-1307, USA.  
%

input boxes

noderadius := 1;
loopcurl := 1.5;

% This macros extend the possible boxes with "node". "node" is like a "circleit",
% but the size of the enclosing circle is fixed by the global variable noderadius.

vardef fixedcirc_(suffix $) = enddef;

vardef node@# text l =
  beginbox_("thecirc_","fixedcirc_",@#,l);
  generic_declare(pair) _n.n, _n.s, _n.e, _n.w;
  @#radius := noderadius;
  @#e-@#c = @#c-@#w = (@#radius,0);
  @#n-@#c = @#c-@#s = (0,@#radius);
  endbox_(clearc_,@#);
enddef;

% Returns whether the provided suffix is empty.

vardef isempty@# =
	(length(str @#) = 0)
enddef;

% Produces the text fragment that will cut correctly a path between nodes s and t.

def cutarc(suffix s,t) = cutbefore bpath.s cutafter bpath.t enddef;

% Returns the correct path from s to t using middle as path instruction; 
% the resulting path must be drawn by a draw/drawarrow/drawdblarrow command.

def arc(suffix s)(text middle)(suffix t) = 
	(s.c middle t.c cutarc(s, t))
enddef;

% Labels in a given position an arc with a given label and direction (difference
% between node centres). The default position is top.

vardef labelpath@#(expr p)(text l)(expr d) =
	label 
		if isempty@#: 
			if (angle d >= 45) and (angle d < 135) or (angle d >= -135) and (angle d < -45): rt
			else: top
			fi
		else: @#
		fi
			(l, point .5*length p of p);
enddef;

% Same as arc, but additionally places a label on the arc. The label
% position is decided by the suffix after larc, if present (default: top).

vardef larc@#(suffix s)(text middle)(suffix t)(text l) = 
	save p; path p; p := arc(s)(middle)(t);
	labelpath@#(p)(l)(s.c - t.c);
	p
enddef;

% Returns a loop in direction at x in direction d.

vardef loop(suffix x)(expr d) =
	if unknown x.radius: x.radius := 1/2 length(x.n-x.s) fi;
	(x.c{curl loopcurl}..x.c+3d*x.radius..{curl loopcurl}x.c cutarc(x, x))
enddef;

% Support macros. Return additional useful points on the bounding box of p. The last
% four, in particular, are useful when the box is a circle.

def north expr p = 1/2[ulcorner p, urcorner p] enddef;
def south expr p = 1/2[llcorner p, lrcorner p] enddef;
def east expr p = 1/2[urcorner p, lrcorner p] enddef;
def west expr p = 1/2[ulcorner p, llcorner p] enddef;
def northwest expr p = (ulcorner p - center p)/sqrt2 + center p enddef;
def northeast expr p = (urcorner p - center p)/sqrt2 + center p enddef;
def southwest expr p = (llcorner p - center p)/sqrt2 + center p enddef;
def southeast expr p = (lrcorner p - center p)/sqrt2 + center p enddef;

% Finds the right point on a loop to attach a label. The suffix is the same
% as that of lloop, p is the loop, and d its direction.

vardef looplpoint@#(suffix x)(expr p, d) =
		if isempty@#:
			if (angle d >= 45) and (angle d < 135): north p
			elseif (angle d >= 135) or (angle d < -135): west p
			elseif (angle d >= -135) and (angle d < -45): south p
			else: east p
			fi
		else:
			if str @# = "top":     north p
			elseif str @# = "bot": south p
			elseif str @# = "lft": west p
			elseif str @# = "rt":  east p
			elseif str @# = "llft": if unknown x.sw: southwest p else: llcorner p fi
			elseif str @# = "lrt":  if unknown x.se: southeast p else: lrcorner p fi
			elseif str @# = "ulft": if unknown x.nw: northwest p else: ulcorner p fi
			elseif str @# = "urt":  if unknown x.ne: northeast p else: urcorner p fi
			else: center p
			fi
		fi
enddef;

% Draws a loop with given label at x in direction d. The label
% position is decided by the suffix after larc, if present, or it's derived
% from d. Note that by scaling d you can get larger loops.

vardef lloop@#(suffix x)(expr d)(text l) =
	save p; path p; p := loop(x, d);
	label.
		if isempty@#:
			if (angle d >= 45) and (angle d < 135): top
			elseif (angle d >= 135) or (angle d < -135): lft
			elseif  (angle d >= -135) and (angle d < -45): bot
			else: rt 
		fi 
		else: @# fi
			(l, looplpoint@#(x, (bbox p), d));
	p
enddef;
