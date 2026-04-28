import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'Gestion Étudiants',
  description: 'Application de gestion des étudiants et départements',
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="fr">
      <body className="bg-gray-50 min-h-screen">
        {/* Barre de navigation */}
        <nav className="bg-blue-700 text-white px-6 py-4 shadow-md">
          <div className="max-w-5xl mx-auto flex items-center gap-8">
            <span className="font-bold text-xl">🎓 EtudiantsApp</span>
            <a href="/etudiants" className="hover:underline">Étudiants</a>
            <a href="/departements" className="hover:underline">Départements</a>
          </div>
        </nav>

        <main className="max-w-5xl mx-auto px-6 py-8">
          {children}
        </main>
      </body>
    </html>
  );
}
