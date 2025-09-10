import React from 'react';
import './Card.css';
import { Button } from '../../atoms/Button/Button';

interface CardProps {
  title: string;
  description: string;
  imageUrl?: string;
  ctaText?: string;
  onCtaClick?: () => void;
  className?: string;
}

export const Card: React.FC<CardProps> = ({
  title,
  description,
  imageUrl,
  ctaText = 'Learn More',
  onCtaClick,
  className = ''
}) => {
  return (
    <div className={`card ${className}`}>
      {imageUrl && (
        <div className="card-image">
          <img src={imageUrl} alt={title} />
        </div>
      )}
      <div className="card-content">
        <h3 className="card-title">{title}</h3>
        <p className="card-description">{description}</p>
        {onCtaClick && (
          <div className="card-actions">
            <Button variant="outline" size="small" onClick={onCtaClick}>
              {ctaText}
            </Button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Card;
