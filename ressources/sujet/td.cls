\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{td}[2013/11/13 v1.1 Fiches de TD]
\DeclareOption{10pt}{\PassOptionsToClass{\CurrentOption}{article}}
\DeclareOption{12pt}{\PassOptionsToClass{\CurrentOption}{article}}

% Pour contrôler l'affichage (ou non) des solutions
% si la variable \nosolflag n'est pas définie, les solutions sont affichées
% pour cacher les solutions :
%    - passer l'option [nosol] dans le \documentclass
% ou - en ligne de commande : xelatex "\def\nosolflag{}\input{nom_fichier}"
\newif\ifnosol
\DeclareOption{nosol}{\def\nosolflag{}}

\ProcessOptions
\LoadClass[a4paper]{article}

% ***************************************************************************
% *** Paquets
% ***************************************************************************

\RequirePackage{iftex}
% Déclarer les encodages correctement pour pdflatex
\ifPDFTeX
	\RequirePackage[utf8]{inputenc}
\fi
\RequirePackage{palatino}
\RequirePackage[frenchb]{babel}
\RequirePackage[margin=2cm]{geometry} % pour réduire les marges
\RequirePackage{amsthm, amsmath,amsfonts, amssymb, latexsym}
\RequirePackage{graphicx,psfrag}
\RequirePackage{stmaryrd}
\RequirePackage{listings}
\RequirePackage{pifont}
\RequirePackage{color}
\RequirePackage{url}
\RequirePackage{comment}
\RequirePackage{etoolbox}
\RequirePackage{titlesec}
\RequirePackage{suffix}
\RequirePackage{tikz}
\RequirePackage{needspace}
\ifXeTeX
	\RequirePackage{fontspec}
\fi

% ***************************************************************************
% *** Options
% ***************************************************************************

%\setlength{\parindent}{10pt}
\lstset{
	basicstyle=\normalsize\ttfamily\NoAutoSpacing,
	showspaces=false,
	showstringspaces=false,
	columns=fullflexible,
	tabsize=2,
	frame=single,
	breaklines=true,
	breakatwhitespace=true,
	keepspaces,
}

% ***************************************************************************
% *** Environnements
% ***************************************************************************

\titleformat*{\section}{\large\bfseries}
\titleformat*{\subsection}{\bfseries}
\titleformat*{\subsubsection}{\bfseries}

% *** Exercice
% \exer
% \exsection (et \exsection*)
% \exsubsection (et \exsubsection*)
\newcounter{excounter}
\newcounter{exsectioncounter}[excounter]
\newcounter{exsubsectioncounter}[exsectioncounter]

\newcommand{\exer}[1][]{
	\needspace{5\baselineskip}
	\refstepcounter{excounter}
	\vspace{\baselineskip}
	\noindent
	{\normalsize{\bfseries Exercice~\arabic{excounter}.}\hfill{\em #1}}
	\vspace{.5\baselineskip}

}
\newcommand{\exsection}[1]{
	\needspace{4\baselineskip}
	\refstepcounter{exsectioncounter}
	\vspace{.5\baselineskip}
	\noindent
	{\centering\normalsize{\bfseries \Alph{exsectioncounter}. #1}}
	\vspace{.5\baselineskip}

}
\WithSuffix\newcommand\exsection*[1]{
	\needspace{4\baselineskip}
	\vspace{.5\baselineskip}
	\noindent
	{\normalsize{\bfseries #1}}	
	\vspace{.5\baselineskip}

}
\newcommand{\exsubsection}[1]{
	\refstepcounter{exsubsectioncounter}
	\vspace{.5\baselineskip}
	{\normalsize \em\bfseries{\arabic{exsubsectioncounter}. #1}}
	
	\vspace{.5\baselineskip}
}
\WithSuffix\newcommand\exsubsection*[1]{
	\vspace{.5\baselineskip}
	{\normalsize \em\bfseries{#1}}
	
	\vspace{.5\baselineskip}
}

% *** Question
% \begin{quest} \end{quest} (et \begin{quest*} \end{quest*})

% \newcommand*\circled[1]{\tikz[baseline=(char.base)]{
% \node[shape=circle,fill=black,inner sep=0pt, minimum size=\widthof{100}, text=white] (char) {#1};}}

\newcommand*\circled[1]{\tikz[baseline=(char.base)]{
\node[shape=circle,fill=black,inner sep=0pt, minimum size=width("100"), text=white] (char) {#1};}}

\newcounter{questcounter}[excounter]
\newenvironment{quest}
{
	\refstepcounter{questcounter}
	\list{}{\leftmargin=20pt}\item
	\textbf{\circled{\arabic{questcounter}}}
}{
	\endlist
}
\newenvironment{quest*}
{
	\list{}{\leftmargin=20pt}\item
	\textbf{\circled{\large\ding{46}}}
}{
	\endlist
}

% *** Réponse
% \begin{sol} \end{sol}
% Attention: pas d'espaces avant \begin{sol} et \end{sol}
\definecolor{light-gray}{gray}{0.4}
\definecolor{color-blue}{RGB}{48, 99, 159}
\newenvironment{sol}
{
	\list{}{\leftmargin=20pt}\color{color-blue}\item
	\textbf{Solution :}
}{
	\endlist
}
\ifdefined\nosolflag
\excludecomment{sol}
\fi

% ***************************************************************************
% *** Commandes
% ***************************************************************************

\newcommand{\ZZ}{\mathbb{Z}}
\newcommand{\NN}{\mathbb{N}}
\newcommand{\st}{\ |\ }
\renewcommand{\epsilon}{\varepsilon}
\newcommand{\num}{n\textsuperscript{o} }
\newcommand{\rmq}{\textbf{Remarque~: }}
\newcommand{\ind}{\textbf{Indication~: }}
\renewcommand{\theenumi}{\alph{enumi}}
\renewcommand{\title}[1]{
	\noindent
	{\small\TDmodule}
	\hfill{\small\TDresponsables}
	\vspace{.5cm}
	\hrule
	\begin{center}
		{\large\bf #1}
	\end{center}
	\hrule
	\vspace{.5cm}
}
% commandes à redéfinir pour l'en-tête
\newcommand{\TDmodule}{} % indique le nom du module
\newcommand{\TDresponsables}{} % indique les noms des responsables

% ***************************************************************************
% *** Fin
% ***************************************************************************

\endinput