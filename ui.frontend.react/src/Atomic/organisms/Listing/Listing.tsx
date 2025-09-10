import React, { useEffect, useState } from 'react';
import './Listing.css';
import { Card } from '../../molecules/Card/Card';

interface ListingItem {
  id: string;
  title: string;
  description: string;
  imageUrl?: string;
  ctaText?: string;
  ctaLink?: string;
}

const MOCK_DATA: ListingItem[] = [
  {
    id: '1',
    title: 'Sample Item 1',
    description:
      'This is a sample description for item 1. It provides some details about what this item is about.',
    imageUrl: 'https://via.placeholder.com/300x200?text=Item+1',
    ctaText: 'View Details',
    ctaLink: '#1',
  },
  {
    id: '2',
    title: 'Sample Item 2',
    description:
      'This is a sample description for item 2. It provides some details about what this item is about.',
    imageUrl: 'https://via.placeholder.com/300x200?text=Item+2',
    ctaText: 'View Details',
    ctaLink: '#2',
  },
  {
    id: '3',
    title: 'Sample Item 3',
    description:
      'This is a sample description for item 3. It provides some details about what this item is about.',
    imageUrl: 'https://via.placeholder.com/300x200?text=Item+3',
    ctaText: 'View Details',
    ctaLink: '#3',
  },
];

interface ListingProps {
  aemEndpoint?: string;
  title?: string;
  columns?: number; // 1-4
  className?: string;
}

export const Listing: React.FC<ListingProps> = ({
  aemEndpoint = '',
  title = 'Our Products',
  columns = 3,
  className = '',
}) => {
  const [items, setItems] = useState<ListingItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      if (!aemEndpoint) {
        setItems(MOCK_DATA);
        setLoading(false);
        return;
      }
      try {
        setLoading(true);
        const res = await fetch(aemEndpoint, { headers: { Accept: 'application/json' } });
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const data = await res.json();
        // Adjust this mapping to your AEM model.json payload shape
        const transformed: ListingItem[] = (Array.isArray(data) ? data : data.items || []).map(
          (item: any, idx: number) => ({
            id: String(item.id ?? `item-${idx}`),
            title: item.title ?? `Item ${idx + 1}`,
            description: item.description ?? 'No description available',
            imageUrl:
              item.imageUrl ?? `https://via.placeholder.com/300x200?text=Item+${idx + 1}`,
            ctaText: item.ctaText ?? 'View Details',
            ctaLink: item.ctaLink ?? `#${idx + 1}`,
          })
        );
        setItems(transformed.length ? transformed : MOCK_DATA);
        setError(null);
      } catch (e) {
        console.error('AEM fetch failed:', e);
        setError('Failed to load data from AEM. Showing sample data.');
        setItems(MOCK_DATA);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [aemEndpoint]);

  const cols = Math.max(1, Math.min(4, columns));
  const gridClass = `listing-grid cols-${cols}`;

  if (loading) return <div className="listing-loading">Loading...</div>;

  return (
    <div className={`listing ${className}`}>
      {title && <h2 className="listing-title">{title}</h2>}
      {error && <div className="listing-error">{error}</div>}
      {items.length ? (
        <div className={gridClass}>
          {items.map((it) => (
            <div key={it.id} className="listing-item">
              <Card
                title={it.title}
                description={it.description}
                imageUrl={it.imageUrl}
                ctaText={it.ctaText}
                onCtaClick={() => (window.location.href = it.ctaLink || '#')}
              />
            </div>
          ))}
        </div>
      ) : (
        <div className="listing-empty">No items to display</div>
      )}
    </div>
  );
};

export default Listing;
